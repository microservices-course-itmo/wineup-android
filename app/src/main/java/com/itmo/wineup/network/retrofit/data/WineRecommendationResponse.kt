package com.itmo.wineup.network.retrofit.data

import com.google.gson.annotations.SerializedName

data class WineRecommendationResponse (

    @SerializedName("wine_position")
    val winePosition: WinePositionResponse,

    val recommendations: List<WinePositionResponse>

)