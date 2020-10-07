package com.itmo.wineup.features.catalog.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.*
import com.itmo.wineup.features.catalog.presentation.adapters.WinesAdapter
import com.itmo.wineup.features.catalog.presentation.filters.adapters.FiltersAdapter


class CatalogFragment : Fragment() {

    companion object {
        fun newInstance() =
            CatalogFragment()
    }

    private lateinit var viewModel: CatalogViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var filtersRecyclerView: RecyclerView

    private val adapter = WinesAdapter(mutableListOf())

    private val filterAdapter = FiltersAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.vineListRecycler)
        recyclerView.adapter = adapter
        filtersRecyclerView = view.findViewById(R.id.filterRecycler)
        filtersRecyclerView.layoutManager =
            GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        filtersRecyclerView.adapter = filterAdapter

        filtersRecyclerView.addItemDecoration(
            DividerItemDecoration(
                filtersRecyclerView.context,
                LinearLayout.VERTICAL
            )
        )
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)
        viewModel.wineList.observe(viewLifecycleOwner, Observer(this::renderVineList))
        viewModel.wineColorList.observe(viewLifecycleOwner, Observer(this::colorFilter))
        viewModel.wineSugarList.observe(viewLifecycleOwner, Observer(this::sugarFilter))
        viewModel.countriesList.observe(viewLifecycleOwner, Observer(this::countriesFilter))
        viewModel.recommendationList.observe(viewLifecycleOwner, Observer(this:: recommendationFilter))
        viewModel.priceValue.observe(viewLifecycleOwner, Observer(this:: priceFilter))
        filterAdapter.updateList(getFiltersList())
        viewModel.setWines()


    }

    private fun renderVineList(vineList: List<WineModel>) {
        adapter.updateList(vineList)
    }

    private fun colorFilter(vineList: Set<WineColor>) {
        //Toast.makeText(context, "Color : $vineList", Toast.LENGTH_LONG).show()
    }

    private fun sugarFilter(sugarList: Set<WineSugar>) {
        //Toast.makeText(context, "Sugar : $sugarList", Toast.LENGTH_LONG).show()
    }

    private fun countriesFilter(countriesList: List<String>) {
        //Toast.makeText(context, "Countries : $countriesList", Toast.LENGTH_LONG).show()
    }

    private fun recommendationFilter(recommendation: Recommendation) {
        //Toast.makeText(context, "Recommendation : $recommendation", Toast.LENGTH_LONG).show()
    }

    private fun priceFilter(price: WinePriceFilter) {
        //Toast.makeText(context, "Price : $price", Toast.LENGTH_LONG).show()
    }

    private fun getFiltersList() = listOf(
        "Все фильтры",
        "Рекомендованные",
        "Цена",
        "Страна",
        "Цвет",
        "Содержание сахара"
    )

}
