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
    private val sortOfGrape = itemView.grapeName
    private val volume = itemView.productVolume
    private val country = itemView.productCountry
    private val description = itemView.productDescription
    private val personalMatch = itemView.personalMatch
    private val shop = itemView.shop
    private val discount = itemView.discount
    private val oldPrice = itemView.oldPrice
    private val newPrice = itemView.newPrice
    private val year = itemView.year
    private val rating = itemView.ratingBar
    private val toFavorites = itemView.toFavorites


    fun bind(model: WineModel) {
        with(itemView.context) {
            name.text = model.name
            description.text = getString(R.string.wine_item_description, model.amountOfSugar, model.color)
            volume.text = model.volume
            personalMatch.text = getString(R.string.wine_item_relevance, model.personalMatch)
            oldPrice.text = getString(R.string.wine_item_old_price, model.price)
            discount.text = getString(R.string.wine_item_discount, model.discount)
            newPrice.text = getString(R.string.wine_item_price, (model.price - model.price * model.discount / 100))
            rating.rating = model.rate
            shop.text = model.shop
            sortOfGrape.text = model.sortOfGrape
            country.text = model.country
            year.text = getString(R.string.wine_item_year, model.year)
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
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                    Log.d("TEST_img", p0.toString())
                    return false
                }

                override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                    Log.d("TEST_img", "OnResourceReady")
                    //do something when picture already loaded
                    return false
                }
            })
            .into(image)

//        Glide.with(itemView.context)
//            .load(model.tradeMarkUrl)
//            .override(trademark.width, trademark.height)
//            .into(trademark)
    }
}