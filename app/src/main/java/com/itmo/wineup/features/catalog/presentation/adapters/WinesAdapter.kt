package com.itmo.wineup.features.catalog.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.presentation.adapters.view_holders.WineViewHolder
import com.itmo.wineup.features.catalog.presentation.filters.models.CountryModel
import java.util.*

class WinesAdapter(private var vinesList: MutableList<WineModel>) :
    RecyclerView.Adapter<WineViewHolder>(), Filterable {

    private val filteredList = mutableListOf<WineModel>().apply { addAll(vinesList) }

    fun updateList(newVines: List<WineModel>) {
        vinesList.apply {
            clear()
            addAll(newVines)
        }
        filteredList.apply {
            clear()
            addAll(vinesList)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WineViewHolder =
        WineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_wine, parent, false)
        )

    override fun onBindViewHolder(
        holder: WineViewHolder,
        position: Int,
    ) = holder.bind(filteredList[position])

    override fun getFilter(): Filter = filter

    private val filter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            Log.d("Filtering", constraint.toString() + " eee")
            val results = mutableListOf<WineModel>()
            if (constraint == null || constraint.isEmpty()) {
                results.addAll(vinesList)
            }
            else {
                val pattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                for (wine in vinesList) {
                    if (wine.name.toLowerCase(Locale.ROOT).contains(pattern)) results.add(wine)
                }
            }
            Log.d("Results on start", results.toString())
            return FilterResults().apply { values = results }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredList.clear()
            val result = results?.values as List<WineModel>?
            Log.d("Results on end", result.toString())
            filteredList.addAll(result ?: emptyList())
            Log.d("Filtered list", filteredList.toString())
            notifyDataSetChanged()
        }

    }

}