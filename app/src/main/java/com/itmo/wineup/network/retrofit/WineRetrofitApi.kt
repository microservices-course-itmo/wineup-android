package com.itmo.wineup.network.retrofit

import com.itmo.wineup.network.retrofit.data.WineResponse
import retrofit2.http.GET

interface WineRetrofitApi {

    @GET("wine/true")
    suspend fun getWineList(): List<WineResponse>

}