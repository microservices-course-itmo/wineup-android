package com.itmo.wineup.network.retrofit.user

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserService {

    private const val baseUrl = "http://77.234.215.138:48080/user-service/"
    private val retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()

    fun api(): UserServiceApi = retrofit.create(UserServiceApi::class.java)

}