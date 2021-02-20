package com.itmo.wineup.features.catalog.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.presentation.adapters.view_holders.LoadStateViewHolder

class CatalogLoadStateAdapter(
private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.load_state_item, parent, false), retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}