package com.itmo.wineup.features.catalog.presentation.adapters.view_holders

import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel
import kotlinx.android.synthetic.main.item_vine.view.*

class VineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val image = itemView.leftImage
    private val name = itemView.productName
    private val description = itemView.productDescription
    private val volume = itemView.productVolume
    private val relevance = itemView.productRelevance
    private val oldPrice = itemView.oldPrice
    private val newPrice = itemView.newPrice
    private val discount = itemView.discount
    private val rating = itemView.ratingBar
    private val trademark = itemView.trademark

    fun bind(model: WineModel) {
        with(itemView.context) {
            name.text = model.name
            description.text = getString(R.string.wine_item_description, model.country, model.color, model.amountOfSugar)
            volume.text = model.volume
            relevance.text = getString(R.string.wine_item_relevance, model.personalMatch)
            oldPrice.text = getString(R.string.wine_item_price, model.price)
            discount.text = getString(R.string.wine_item_discount, model.discount)
            newPrice.text = getString(R.string.wine_item_price, (model.price - model.price * model.discount / 100))
            rating.rating = model.rate
        }

        oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        Glide.with(image)
            .load(model.imageUrl)
            .into(image)
        Glide.with(trademark)
            .load(model.tradeMarkUrl)
            .into(trademark)
    }
}