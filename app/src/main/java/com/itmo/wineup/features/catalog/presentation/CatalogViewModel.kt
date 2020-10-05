package com.itmo.wineup.features.catalog.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.Recommendation
import com.itmo.wineup.features.catalog.models.WineColor
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.models.WineSugar

class CatalogViewModel: ViewModel() {

    private val getWineListUseCase = GetWineListUseCase()

    val wineList = MutableLiveData<List<WineModel>>()
    val wineColorList = MutableLiveData<Set<WineColor>>()
    val wineSugarList = MutableLiveData<Set<WineSugar>>()
    val countriesList = MutableLiveData<List<String>>()
    val recommendationList = MutableLiveData<Recommendation>()

    fun setWines() {
        wineList.value = getWineListUseCase.invoke()
    }

}