package com.itmo.wineup.network.retrofit.data

import com.google.gson.annotations.SerializedName

data class WineResponse (

    val avg: Float,

    @SerializedName("brand_id")
    val brandId: String,

    val color: String,

    @SerializedName("grape_id")
    val grapeId: String,

    val name: String,

    @SerializedName("producer_id")
    val producerId: String,

    @SerializedName("region_id")
    val regionId: String,

    val sugar: String,

    @SerializedName("wine_id")
    val wineId: String,

    val year: Int

)