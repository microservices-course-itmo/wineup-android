package com.itmo.wineup.features.catalog.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.WineModel

class CatalogViewModel: ViewModel() {

    private val getWineListUseCase = GetWineListUseCase()

    val wineList = MutableLiveData<List<WineModel>>()

    init {
        wineList.value = getWineListUseCase.invoke()
    }

}