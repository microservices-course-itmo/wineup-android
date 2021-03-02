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
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.wine_info.WineInfoActivity
import com.itmo.wineup.network.retrofit.user.FavoritesRepository
import kotlinx.android.synthetic.main.item_wine.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        const val WINE_MODEL_TAG = "WINE_MODEL"
    }

    private val image = itemView.leftImage

    private val name = itemView.productName
    private val sortOfGrape = itemView.grapeName
    private val info = itemView.productInfo
    private val personalMatch = itemView.personalMatch
    private val shop = itemView.shop
    private val discount = itemView.discount
    private val oldPrice = itemView.oldPrice
    private val newPrice = itemView.newPrice
    private val year = itemView.year
    private val toFavorites = itemView.toFavorites


    fun bind(model: WineModel) {
        with(itemView.context) {
            name.text = model.name
            val volumeString = getString(R.string.wine_item_volume, model.volume).replace(',', '.')
            info.text = getString(R.string.wine_item_description, model.country, model.amountOfSugar, model.color, volumeString)
            personalMatch.text = getString(R.string.wine_item_relevance, model.personalMatch)
            if (model.oldPrice == 0f || model.oldPrice == model.price) {
                discount.visibility = View.GONE
                oldPrice.visibility = View.GONE
            }
            else {
                oldPrice.text = getString(R.string.wine_item_old_price, model.oldPrice)
                oldPrice.visibility = View.VISIBLE
                discount.visibility = View.VISIBLE
            }
            discount.text = getString(R.string.wine_item_discount, model.discount)
            newPrice.text = getString(R.string.wine_item_price, model.price)
            shop.text = model.shop
            sortOfGrape.text = model.sortOfGrape
            year.text = getString(R.string.wine_item_year, model.year)
            if (model.isFavorite) toFavorites.setImageResource(R.drawable.ic_like_red)
            else toFavorites.setImageResource(R.drawable.ic_like)
        }
        toFavorites.setOnClickListener {
            if (model.isFavorite) {
                model.isFavorite = false
                toFavorites.setImageResource(R.drawable.ic_like)
                FavoritesRepository.removeFromFavorites(model.positionId)
            } else {
                model.isFavorite = true
                toFavorites.setImageResource(R.drawable.ic_like_red)
                FavoritesRepository.addToFavorites(model.positionId)
            }
        }

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

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, WineInfoActivity::class.java)
            intent.putExtra(WINE_MODEL_TAG, model)
            itemView.context.startActivity(intent)
        }

//        Glide.with(itemView.context)
//            .load(model.tradeMarkUrl)
//            .override(trademark.width, trademark.height)
//            .into(trademark)
    }
}