package com.itmo.wineup.features.favorites.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.favorites.presentation.models.FavoriteSortModel

class FavoritesViewModel: ViewModel() {

    //todo: replace with getting favorite wines
    private val getWineListUseCase = GetWineListUseCase()

    val wineList = MutableLiveData<List<WineModel>>()
    val selectedSort = MutableLiveData<FavoriteSortModel>()


    fun setWines() {
        wineList.value = getWineListUseCase.invoke()
    }


}