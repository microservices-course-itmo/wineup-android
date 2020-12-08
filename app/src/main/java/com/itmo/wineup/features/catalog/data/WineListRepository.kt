package com.itmo.wineup.features.catalog.data

import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.network.retrofit.WineRetrofitApi

class WineListRepository(private val api: WineRetrofitApi) {


    suspend fun getList(positionStart: Int, positionEnd: Int) = getListFromApi(positionStart, positionEnd)

    private suspend fun getListFromApi(positionStart: Int, positionEnd: Int) = api.getWinePositionList(positionStart, positionEnd)

    fun getHardcodedList(): List<WineModel> {
        val wines = arrayListOf<WineModel>()
        for (i in 0..9) {
            wines.add(
                WineModel(
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
                    ""
                )
            )
        }
        return wines

    }

}