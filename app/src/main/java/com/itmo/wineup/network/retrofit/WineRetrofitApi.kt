package com.itmo.wineup.network.retrofit

import com.itmo.wineup.network.retrofit.data.WinePositionResponse
import com.itmo.wineup.network.retrofit.data.WineResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface WineRetrofitApi {

    @Headers("accessToken: 123")
    @GET("wine/true")
    suspend fun getWineList(@Body body: String): List<WineResponse>

    @Headers("accessToken: 123")
    @GET("position/true/")
    suspend fun getWinePositionList(/*@Body body: String = ""*/): List<WinePositionResponse>

}
