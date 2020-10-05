package com.itmo.wineup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itmo.wineup.features.catalog.presentation.CatalogFragment
import com.itmo.wineup.features.catalog.presentation.filters.FilterColorFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        searchView.setOnClickListener{

            val fragment = FilterColorFragment()
            openFilterFragment(fragment)
        }
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_main -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_catalog -> {
                val fragment = CatalogFragment()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {

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
    private fun openFilterFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navHostFiltersFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}