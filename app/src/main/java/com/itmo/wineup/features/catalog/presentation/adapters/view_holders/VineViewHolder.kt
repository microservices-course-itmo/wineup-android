package com.itmo.wineup.features.catalog.presentation.adapters.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel

class VineViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    private val wineName = itemView.findViewById<TextView>(R.id.productName)

    fun bind(wine: WineModel) {
        wineName.text = wine.name

    }
}