package com.itmo.wineup.features.catalog.presentation.adapters.view_holders

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.load_state_item.view.*

class LoadStateViewHolder(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val progressBar: ProgressBar = itemView.progress
    private val retry: Button = itemView.retry_button
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
        Log.d("Foo", "Ter $loadState")
        if (loadState is LoadState.Loading) {
            progressBar.visibility = View.VISIBLE
            retry.visibility = View.GONE
        }
        else if (loadState is LoadState.Error) {
            progressBar.visibility = View.GONE
            retry.visibility = View.VISIBLE
        }
    }
}