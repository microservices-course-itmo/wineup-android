package com.itmo.wineup.features.catalog.presentation.adapters.view_holders

import android.content.Intent
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.FeedbackModel
import com.itmo.wineup.features.wine_info.WineInfoActivity
import kotlinx.android.synthetic.main.item_feedback.view.*

class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        const val FEEDBACK_MODEL_TAG = "FEEDBACK_MODEL"
    }

    private val name = itemView.nameComment
    private val rating = itemView.ratingBar
    private val comment = itemView.commentFeedback
    private val date = itemView.dateComment

    fun bind(model: FeedbackModel){
        with(itemView.context) {
            name.text = model.name
            rating.rating = model.rate
            comment.text = model.comment
            date.text = model.date
        }

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, WineInfoActivity::class.java)
            intent.putExtra(FEEDBACK_MODEL_TAG, model)
            itemView.context.startActivity(intent)
        }
    }
}