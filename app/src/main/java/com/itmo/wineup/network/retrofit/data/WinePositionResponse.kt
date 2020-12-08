package com.itmo.wineup.network.retrofit.data

import com.google.gson.annotations.SerializedName

data class WinePositionResponse(

    @SerializedName("actual_price")
    val actualPrice: Float,

    val description: String,

    val image: String,

    @SerializedName("link_to_wine")
    val linkToWine: String,

    val price: Float,

    val shop: ShopResponse,

    val volume: Float,

    val wine: WineResponse,

    val gastronomy: String,

    @SerializedName("wine_position_id")
    val winePositionId: String

)