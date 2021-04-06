package com.itmo.wineup.features.catalog.models

import java.io.Serializable

data class WineModel(
    val id: String,
    val name: String,
    val country: String,
    val color: String,
    val amountOfSugar: String,
    val volume: Float,
    val personalMatch: Int,
    val rate: Float,
    val price: Float,
    val oldPrice: Float,
    val discount: Int,
    val imageUrl: String,
    val tradeMarkUrl: String,
    val shop: String,
    val year: Int,
    val sortOfGrape: String,
    var isFavorite: Boolean,
    var gastronomy: String,
    var description: String,
    val positionId: String
) : Serializable