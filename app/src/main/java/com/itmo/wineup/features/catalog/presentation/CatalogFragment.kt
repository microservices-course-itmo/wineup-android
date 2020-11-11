package com.itmo.wineup.features.catalog.presentation

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.*
import com.itmo.wineup.features.catalog.presentation.adapters.WinesAdapter
import com.itmo.wineup.features.catalog.presentation.filters.adapters.FiltersAdapter
import com.itmo.wineup.network.retrofit.data.State


class CatalogFragment : Fragment() {

    companion object {
        fun newInstance() =
            CatalogFragment()

        val typeface_thin = Typeface.create("sans-serif-thin", Typeface.NORMAL)
        val typeface_normal = Typeface.create("sans-serif", Typeface.NORMAL)
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
            LinearLayoutManager(activity, GridLayoutManager.HORIZONTAL, false)
        filtersRecyclerView.adapter = filterAdapter

        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)
        viewModel.wineList.observe(viewLifecycleOwner, Observer(this::renderWineList))
        viewModel.wineColorList.observe(viewLifecycleOwner, Observer(this::colorFilter))
        viewModel.wineSugarList.observe(viewLifecycleOwner, Observer(this::sugarFilter))
        viewModel.countriesList.observe(viewLifecycleOwner, Observer(this::countriesFilter))
        viewModel.recommendationList.observe(
            viewLifecycleOwner,
            Observer(this::recommendationFilter)
        )
        viewModel.priceValue.observe(viewLifecycleOwner, Observer(this::priceFilter))
        filterAdapter.updateList(getFiltersList())
        viewModel.setWines()


    }

    private fun renderWineList(state: State) {
        when (state) {
            is State.Loading -> {
                Log.i("testing", "state = LOADING")
                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                //todo show skeletons or loader
            }
            is State.Success -> {
                adapter.updateList(state.data)
                Log.i("testing", "state = success")
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            }
            is State.Error -> {
                Log.i("testing", "state = error, message = ${state.message}")
                Toast.makeText(requireContext(), "error: ${state.message}", Toast.LENGTH_LONG).show()

                //todo alert or stub
            }
        }

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
        "Рекомендованные",
        "Цена",
        "Страна",
        "Цвет",
        "Содержание сахара"
    )

}
