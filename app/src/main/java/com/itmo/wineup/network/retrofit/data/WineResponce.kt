package com.itmo.wineup.network.retrofit.data

import com.google.gson.annotations.SerializedName

data class WineResponse(

    val avg: Float,

    val brand: BrandResponse,

    val color: String,

    val grape: List<GrapeResponse>,

    val name: String,

    val producer: ProducerResponse,

    val region: List<RegionResponse>,

    val sugar: String,

    @SerializedName("wine_id")
    val wineId: String,

    val year: Int

)