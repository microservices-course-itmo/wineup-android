package com.itmo.wineup.network.retrofit

import com.itmo.wineup.network.retrofit.data.WinePositionResponse
import com.itmo.wineup.network.retrofit.data.WineResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface WineRetrofitApi {

    @Headers("accessToken: 123")
    @GET("wine/true/")
    suspend fun getWineList(
        @Query("from") from: Int,
        @Query("to") to: Int
    ): List<WineResponse>

    @Headers("accessToken: 123")
    @GET("position/true/")
    suspend fun getWinePositionList(
        @Query("from") from: Int,
        @Query("to") to: Int
    ): List<WinePositionResponse>

}
