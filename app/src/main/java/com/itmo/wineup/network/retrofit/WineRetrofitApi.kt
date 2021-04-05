package com.itmo.wineup.network.retrofit

import com.itmo.wineup.TokenMaster
import com.itmo.wineup.network.retrofit.data.WinePositionResponse
import com.itmo.wineup.network.retrofit.data.WineResponse
import retrofit2.Call
import retrofit2.http.*

interface WineRetrofitApi {

    @GET("position/true/trueSettings")
    suspend fun getWinePositionList(
        @Query("page") page: Int,
        @Query("amount") amount: Int,
        @Query("filterBy") params: String,
        @Header("Authorization") accessToken: String = "Bearer ${TokenMaster.accessToken}"
    ): List<WinePositionResponse>

    @GET("position/true/favourites")
    suspend fun getFavoritesList(
        @Query("favouritePosition") list: List<String>,
        @Header("Authorization") accessToken: String = "Bearer ${TokenMaster.accessToken}"
    ): List<WinePositionResponse>

    @GET("rec/true/byId/{id}")
    suspend fun getRecommendationList(
        @Path("id") id: String,
        @Header("Authorization") accessToken: String = "Bearer ${TokenMaster.accessToken}"
    ): List<WinePositionResponse>

}
