package com.itmo.wineup.features.catalog.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.FeedbackModel
import com.itmo.wineup.features.catalog.presentation.adapters.view_holders.FeedbackViewHolder

class FeedbackAdapter(private var feedbackList: MutableList<FeedbackModel>):
    RecyclerView.Adapter<FeedbackViewHolder>() {

    fun updateList(newFeedback: List<FeedbackModel>) {
        feedbackList.apply {
            clear()
            addAll(newFeedback)
        }
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int = feedbackList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder =
        FeedbackViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_feedback, parent, false)
        )

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) =
        holder.bind(feedbackList[position])
}