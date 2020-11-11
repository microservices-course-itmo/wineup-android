package com.itmo.wineup.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "http://77.234.215.138:48080/catalog-service/"

    val wineApi = getRetrofit()
        .create(WineRetrofitApi::class.java)

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}