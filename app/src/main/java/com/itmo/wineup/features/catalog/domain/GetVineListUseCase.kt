package com.itmo.wineup.features.catalog.domain

import com.itmo.wineup.features.catalog.data.VineListRepository

class GetVineListUseCase {

    private val vineListRepository = VineListRepository()

    operator fun invoke() = vineListRepository.getList()

}