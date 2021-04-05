package com.itmo.wineup.features.catalog.data

import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.network.retrofit.WineRetrofitApi

class WineListRepository(private val api: WineRetrofitApi, private val searchParams: String) {


    suspend fun getList(page: Int, amount: Int) = getListFromApi(page, amount, searchParams)

    private suspend fun getListFromApi(page: Int, amount: Int, searchParams: String) = api.getWinePositionList(page, amount, searchParams)

    suspend fun getRecommendationList(id: String) = api.getRecommendationList(id)

    fun getHardcodedList(): List<WineModel> {
        val wines = arrayListOf<WineModel>()
        for (i in 0..9) {
            wines.add(
                WineModel(
                    "id1",
                    "Canti Merlot",
                    "Франция",
                    "красное",
                    "Сухое",
                    0.75f,
                    85,
                    4.5F,
                    2300f,
                    3000f,
                    15,
                    "https://kvz1926.com/img/2018/11/wine-isabella-red-semisweet.jpg",
                    "https://telegra.ph/file/3e1bd647ce4230fa995d9.jpg",
                    "Ароматный мир",
                    2011,
                    "Saubignon Blanc",
                    false,
                    "",
                    "",
                    ""
                )
            )
        }
        return wines

    }

}