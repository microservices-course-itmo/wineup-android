package com.itmo.wineup.features.catalog.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.presentation.adapters.WinesAdapter


class CatalogFragment : Fragment() {

    companion object {
        fun newInstance() =
            CatalogFragment()
    }

    private lateinit var viewModel: CatalogViewModel

    private lateinit var recyclerView: RecyclerView

    private val adapter = WinesAdapter(mutableListOf())

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
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)
        viewModel.wineList.observe(viewLifecycleOwner, Observer(this::renderVineList))
    }

    private fun renderVineList(vineList: List<WineModel>) {
        adapter.updateList(vineList)
    }

}