package com.itmo.wineup.features.catalog.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import com.itmo.wineup.features.catalog.presentation.filters.adapters.CountriesAdapter
import kotlinx.android.synthetic.main.activity_filter_countries.*

class FilterCountriesFragment : Fragment() {

    private lateinit var viewModel: CatalogViewModel

    private val stubCountriesList = listOf(
        "Австралия",
        "Австрия",
        "Аргентина",
        "Германия",
        "Греция",
        "Грузия",
        "Израиль",
        "Испания"
    )
    private val recyclerAdapter = CountriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_filter_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)

        with(country_recycler) {
            layoutManager = LinearLayoutManager(context)
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

    override fun onPause() {
        viewModel.countriesList.value = recyclerAdapter.getCheckedCountries()
        super.onPause()
    }

}