package com.itmo.wineup.features.catalog.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.VineModel
import com.itmo.wineup.features.catalog.presentation.adapters.view_holders.VineViewHolder

class VinesAdapter(private var vinesList: MutableList<VineModel>) :
    RecyclerView.Adapter<VineViewHolder>() {

    fun updateList(newVines: List<VineModel>) {
        vinesList.apply {
            clear()
            addAll(newVines)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = vinesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VineViewHolder =
        VineViewHolder(
            //todo change layout to R.layout.item_vine, it's just for testing recycler
            LayoutInflater.from(parent.context).inflate(R.layout.test_item_vine, parent, false)
        )

    override fun onBindViewHolder(
        holder: VineViewHolder,
        position: Int,
    ) = holder.bind(vinesList[position])

}