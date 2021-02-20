package com.itmo.wineup.features.catalog.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.presentation.adapters.view_holders.WineViewHolder

class CatalogAdapter(diffCallback: DiffUtil.ItemCallback<WineModel>) :
    PagingDataAdapter<WineModel, WineViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WineViewHolder =
        WineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_wine, parent, false)
        )

    override fun onBindViewHolder(holder: WineViewHolder, position: Int, ) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

}