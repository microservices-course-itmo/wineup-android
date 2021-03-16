package com.itmo.wineup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.itmo.wineup.features.auth.USER_ACCESS_INFO
import com.itmo.wineup.features.auth.USER_ACCESS_TOKEN
import com.itmo.wineup.features.auth.USER_CURRENT_ID
import com.itmo.wineup.features.auth.USER_REFRESH_TOKEN
import com.itmo.wineup.features.catalog.presentation.CatalogFragment
import com.itmo.wineup.features.catalog.presentation.filters.FilterColorFragment
import com.itmo.wineup.features.favorites.presentation.FavoritesFragment
import com.itmo.wineup.features.main.presentation.MainFragment
import com.itmo.wineup.features.profile.presentation.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationController = findNavController(R.id.navHostFragment)
        navView.setupWithNavController(navigationController)
        //navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //buttonTest.setOnClickListener { openFilterFragment(FilterColorFragment()) }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching token failed.", task.exception)
            }
            else {
                Log.d("FCM", "Token for this app is ${task.result}")
            }
        }
        val prefs = getSharedPreferences(USER_ACCESS_INFO, Context.MODE_PRIVATE)
        TokenMaster.init(prefs.getString(USER_ACCESS_TOKEN, "")!!, prefs.getString(USER_REFRESH_TOKEN, "")!!)
        val id = prefs.getInt(USER_CURRENT_ID, -1)
        Toast.makeText(baseContext, "Logged in with user id $id", Toast.LENGTH_SHORT).show()
    }

    fun backFromConfirmCodeFragment(){
        navigationController.popBackStack()
    }

    fun openConfirmCodefragment(phone: String){
        val bundle  = bundleOf("phone" to phone)
        navigationController.navigate(R.id.action_navigation_profile_to_confirmCodeFragment, bundle)
    }

}