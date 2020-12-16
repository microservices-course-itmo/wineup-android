package com.itmo.wineup.network.retrofit.user

import com.google.gson.JsonObject
import com.itmo.wineup.TokenMaster
import retrofit2.Call
import retrofit2.http.*

interface UserServiceApi {

    @POST("login")
    fun login(@Body tokenObject: JsonObject) : Call<LoginResponse>

    @POST("registration")
    fun register(@Body requestObject: JsonObject) : Call<LoginResponse>

    @POST("refresh")
    fun refresh(@Query("refreshToken") token: String) : Call<LoginResponse>

    @POST("validate")
    fun validate(@Query("accessToken") token: String) : Call<Unit>

    @GET("users/{id}")
    fun user(@Path("id") id: Int) : Call<UserResponse>

    @GET("users/me")
    fun currentUser(
        @Header("Authorization") accessToken: String = "Bearer ${TokenMaster.accessToken}"
    ) : Call<UserResponse>

    @GET("favorites/list")
    suspend fun favoritesList(
        @Header("Authorization") accessToken: String = "Bearer ${TokenMaster.accessToken}"
    ) : List<String>

    @POST("favorites/{itemId}")
    suspend fun addToFavorites(
        @Path("itemId") wineId : String,
        @Header("Authorization") accessToken: String = "Bearer ${TokenMaster.accessToken}"
    )

    @DELETE("favorites/{itemId}")
    suspend fun removeFromFavorites(
        @Path("itemId") wineId: String,
        @Header("Authorization") accessToken: String = "Bearer ${TokenMaster.accessToken}"
    )

    @DELETE("favorites/clear")
    suspend fun clearFavorites(
        @Header("Authorization") accessToken: String = "Bearer ${TokenMaster.accessToken}"
    )

}