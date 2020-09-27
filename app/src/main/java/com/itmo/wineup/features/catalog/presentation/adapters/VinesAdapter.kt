package com.itmo.wineup.features.catalog.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.presentation.adapters.view_holders.VineViewHolder

class VinesAdapter(private var vinesList: MutableList<WineModel>) :
    RecyclerView.Adapter<VineViewHolder>() {

    fun updateList(newVines: List<WineModel>) {
        vinesList.apply {
            clear()
            addAll(newVines)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = vinesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VineViewHolder =
        VineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_vine, parent, false)
        )

    override fun onBindViewHolder(
        holder: VineViewHolder,
        position: Int,
    ) = holder.bind(vinesList[position])

}