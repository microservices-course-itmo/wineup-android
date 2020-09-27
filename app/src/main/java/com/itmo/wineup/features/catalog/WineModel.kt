package com.itmo.wineup.features.catalog

data class WineModel(
    val name: String,
    val country: String,
    val color: String,
    val amountOfSugar: String,
    val volume: String,
    val personalMatch: Int,
    val rate: Float,
    val price: Int,
    val discount: Int,
)