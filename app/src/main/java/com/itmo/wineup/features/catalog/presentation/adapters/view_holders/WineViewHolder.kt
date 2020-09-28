package com.itmo.wineup.features.catalog.presentation.adapters.view_holders

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
import com.itmo.wineup.features.catalog.models.WineModel
import kotlinx.android.synthetic.main.item_wine.view.*

class WineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
    private val toFavorites = itemView.toFavorites

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
        toFavorites.setOnClickListener(View.OnClickListener {
            if(model.isFavorite){
                model.isFavorite = false
                Glide.with(itemView.context).load(R.drawable.ic_like).into(toFavorites)
            }
            else{
                model.isFavorite = true
                Glide.with(itemView.context).load(R.drawable.ic_like_red).into(toFavorites)
            }

        })

        oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        Glide.with(itemView.context)
            .load(model.imageUrl)
            .override(image.width, image.height)
            .into(image)

        Glide.with(itemView.context)
            .load(model.tradeMarkUrl)
            .override(trademark.width, trademark.height)
            .into(trademark)
    }
}