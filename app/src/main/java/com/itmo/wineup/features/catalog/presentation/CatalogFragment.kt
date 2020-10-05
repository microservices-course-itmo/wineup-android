package com.itmo.wineup.features.catalog.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.presentation.adapters.WinesAdapter
import com.itmo.wineup.features.catalog.presentation.filters.adapters.FiltersAdapter
import kotlinx.android.synthetic.main.fragment_catalog.*


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
        val layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        filtersRecyclerView.layoutManager = layoutManager
        filtersRecyclerView.adapter = filterAdapter

        val dividerItemDecoration = DividerItemDecoration(
            filtersRecyclerView.context,
            LinearLayout.VERTICAL
        )
        filtersRecyclerView.addItemDecoration(dividerItemDecoration);
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)
        viewModel.wineList.observe(viewLifecycleOwner, Observer(this::renderVineList))
        searchView.setOnClickListener {
            findNavController().navigate(R.id.filterCountriesActivity)
        }
    }

    private fun renderVineList(vineList: List<WineModel>) {
        adapter.updateList(vineList)
        filterAdapter.updateList(
            listOf(
                "Все фильтры",
                "Рекомендованные",
                "Цена",
                "Страна",
                "Цвет",
                "Содержание сахара"
            )
        )
    }

}