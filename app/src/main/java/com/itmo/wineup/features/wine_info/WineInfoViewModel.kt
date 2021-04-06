package com.itmo.wineup.features.wine_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.network.retrofit.data.WinePositionConverter
import com.itmo.wineup.network.retrofit.data.WineRecommendationResponse
import kotlinx.coroutines.launch

class WineInfoViewModel : ViewModel() {

    private val getWineListUseCase = GetWineListUseCase()

    val recommendationList = MutableLiveData<List<WineModel>>()

    fun getRecommendationList(id: String) {
      viewModelScope.launch {
          try{
              val list = getWineListUseCase.getRecommendationList(id)
              recommendationList.value = WinePositionConverter.toWineModel(list.recommendations)
          } catch (e: Throwable){
              Log.e("error", "error getting recommendations + ${e.message}")
              //todo show error screen
          }

      }
    }

}