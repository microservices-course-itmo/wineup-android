package com.itmo.wineup.features.catalog.domain

import com.itmo.wineup.features.catalog.data.WineListRepository

class GetWineListUseCase {

    private val wineListRepository = WineListRepository()

    operator fun invoke() = wineListRepository.getList()

}