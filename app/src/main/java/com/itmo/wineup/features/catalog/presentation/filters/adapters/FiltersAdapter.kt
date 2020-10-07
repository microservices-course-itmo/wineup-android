package com.itmo.wineup.features.catalog.presentation.filters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders.FiltersViewHolder

class FiltersAdapter(private var filtersList: MutableList<String>) :
    RecyclerView.Adapter<FiltersViewHolder>() {

    fun updateList(newVines: List<String>) {
        filtersList.apply {
            clear()
            addAll(newVines)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = filtersList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder =
        FiltersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        )

    override fun onBindViewHolder(
        holder: FiltersViewHolder,
        position: Int,
    ) = holder.bind(filtersList[position])

}