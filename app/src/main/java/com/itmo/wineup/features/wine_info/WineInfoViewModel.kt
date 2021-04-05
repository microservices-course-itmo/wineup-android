package com.itmo.wineup.features.wine_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.network.retrofit.data.WinePositionConverter
import kotlinx.coroutines.launch

class WineInfoViewModel : ViewModel() {

    private val getWineListUseCase = GetWineListUseCase()

    val recommendationList = MutableLiveData<List<WineModel>>()

    fun getRecommendationList(id: String) {
      viewModelScope.launch {
          try{
              val list = getWineListUseCase.getRecommendationList(id)
              recommendationList.value = WinePositionConverter.toWineModel(list)
          } catch (e: Throwable){
              //todo show error screen
          }

      }
    }

}