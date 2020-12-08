package com.itmo.wineup.features.catalog.domain

import com.itmo.wineup.network.retrofit.RetrofitBuilder
import com.itmo.wineup.features.catalog.data.WineListRepository

class GetWineListUseCase {

    private val wineListRepository = WineListRepository(RetrofitBuilder.wineApi)

    suspend operator fun invoke(positionStart: Int, positionEnd: Int) = wineListRepository.getList(positionStart, positionEnd)

    //todo: remove, just for test
    fun getHardcodedList() = wineListRepository.getHardcodedList()

}