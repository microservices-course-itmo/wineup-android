package com.itmo.wineup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //buttonTest.setOnClickListener { openFilterFragment(FilterColorFragment()) }
    }


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_main -> {
                    val fragment = MainFragment()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_catalog -> {
                    val fragment = CatalogFragment()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    val fragment = ProfileFragment()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    val fragment = FavoritesFragment()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    fun navigate(id: Int, bundle: Bundle?) {
        navigationController.navigate(id, bundle)
    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navHostFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun openFilterFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navHostFiltersFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}