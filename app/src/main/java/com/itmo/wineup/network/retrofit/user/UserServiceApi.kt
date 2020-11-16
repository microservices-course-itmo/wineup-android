package com.itmo.wineup.network.retrofit.user

import com.google.gson.JsonObject
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

}