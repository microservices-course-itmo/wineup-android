package com.itmo.wineup.network.retrofit.data

import android.util.Log
import com.itmo.wineup.features.catalog.models.WineModel
import kotlin.math.roundToInt

object WinePositionConverter {

    fun toWineModel(list: List<WinePositionResponse>, allFavorites: Boolean = false): List<WineModel> {
        val res = mutableListOf<WineModel>()
        for (response in list) {
            Log.d("Wine", response.wine.name)
            res.add(
                WineModel(
                    name = response.wine.name,
                    color = response.wine.color,
                    country = response.wine.region.firstOrNull()?.country ?: "",//todo
                    amountOfSugar = response.wine.sugar,
                    volume = response.volume,
                    personalMatch = 1, //todo
                    rate = 2.0f, //todo
                    price = response.actualPrice,
                    oldPrice = response.price,
                    discount =  (((response.price - response.actualPrice) / (if (response.price == 0f) 1f else response.price)) * 100).roundToInt(),//todo
                    imageUrl = response.image,
                    tradeMarkUrl = response.wine.brand.name,
                    shop = response.shop.site,
                    year = response.wine.year,
                    sortOfGrape = response.wine.grape.firstOrNull()?.name ?: "",//todo
                    gastronomy = response.gastronomy,
                    description = response.description,
                    isFavorite = allFavorites, //todo
                    positionId = response.winePositionId
                )
            )
        }
        Log.d("List", res.toString())
        return res
    }
}