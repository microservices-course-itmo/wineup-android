package com.itmo.wineup.features.catalog.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itmo.wineup.features.catalog.domain.GetVineListUseCase
import com.itmo.wineup.features.catalog.models.WineModel

class CatalogViewModel: ViewModel() {

    private val getVineListUseCase = GetVineListUseCase()

    val vineList = MutableLiveData<List<WineModel>>()

    init {
        vineList.value = getVineListUseCase.invoke()
    }

}