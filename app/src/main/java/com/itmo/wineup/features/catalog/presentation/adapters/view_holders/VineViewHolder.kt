package com.itmo.wineup.features.catalog.presentation.adapters.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.VineModel

class VineViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    ////todo remove, just for testing Recycler
    private val testText = itemView.findViewById<TextView>(R.id.testText)

    fun bind(vine: VineModel) {
        testText.text = vine.name
    }
}