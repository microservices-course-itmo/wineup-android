package com.itmo.wineup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.itmo.wineup.features.auth.USER_ACCESS_INFO
import com.itmo.wineup.features.auth.USER_ACCESS_TOKEN
import com.itmo.wineup.features.auth.USER_CURRENT_ID
import com.itmo.wineup.features.auth.USER_REFRESH_TOKEN
import com.itmo.wineup.features.profile.model.Profile
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
            } else {
                Log.d("FCM", "Token for this app is ${task.result}")
            }
        }
        val prefs = getSharedPreferences(USER_ACCESS_INFO, Context.MODE_PRIVATE)
        TokenMaster.init(
            prefs.getString(USER_ACCESS_TOKEN, "")!!,
            prefs.getString(USER_REFRESH_TOKEN, "")!!
        )
        val id = prefs.getInt(USER_CURRENT_ID, -1)
        Toast.makeText(baseContext, "Logged in with user id $id", Toast.LENGTH_SHORT).show()
    }

    fun backFromConfirmCodeFragment() {
        navigationController.popBackStack()
    }

    fun openProfileDataEditFragment(
        name: String,
        phone: String,
        cityId: Int
    ) {
        val bundle = bundleOf("profile" to Profile(name, phone, cityId))
        navigationController.navigate(
            R.id.action_navigation_profile_to_profile_data_edit_fragment,
            bundle
        )
    }

    fun openConfirmCodeFragment(phone: String) {
        val bundle = bundleOf("phone" to phone)
        navigationController.navigate(
            R.id.action_navigation_profile_to_confirm_code_fragment,
            bundle
        )
    }

}