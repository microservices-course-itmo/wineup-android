package com.itmo.wineup.features.catalog.presentation.filters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.presentation.filters.adapters.CountriesAdapter
import kotlinx.android.synthetic.main.activity_filter_countries.*

class FilterCountriesActivity : AppCompatActivity() {

    private val stubCountriesList = listOf("Австралия", "Австрия", "Аргентина", "Германия", "Греция", "Грузия", "Израиль", "Испания")
    private val recyclerAdapter = CountriesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_countries)
        with(country_recycler) {
            layoutManager = LinearLayoutManager(this@FilterCountriesActivity)
            adapter = recyclerAdapter
        }
        country_fiter_reset_button.setOnClickListener { recyclerAdapter.reset() }
        country_searchiew.setOnClickListener { country_searchiew.isIconified = false }

        country_searchiew.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                country_searchiew.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recyclerAdapter.filter.filter(newText)
                return false
            }

        })

        setData(stubCountriesList)
    }

    fun setData(data: List<String>) {
        recyclerAdapter.setData(data)
    }

    fun getSelectedCountries() : List<String> = recyclerAdapter.getCheckedCountries()

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.noanimation)
    }
}