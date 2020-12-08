package com.itmo.wineup.features.catalog.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itmo.wineup.network.retrofit.data.WineResponse
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.*
import com.itmo.wineup.network.retrofit.data.State
import com.itmo.wineup.network.retrofit.data.WinePositionResponse
import kotlinx.coroutines.launch

class CatalogViewModel : ViewModel() {

    private val getWineListUseCase = GetWineListUseCase()

    val wineList = MutableLiveData<State>()
    val wineColorList = MutableLiveData<Set<WineColor>>()
    val wineSugarList = MutableLiveData<Set<WineSugar>>()
    val countriesList = MutableLiveData<List<String>>()
    val recommendationList = MutableLiveData<Recommendation>(Recommendation.RECOMMENDED)
    val priceValue = MutableLiveData<WinePriceFilter>()


    fun setWines() {
        wineList.value = State.Loading  
        viewModelScope.launch {
            try {
                val responseList = getWineListUseCase.invoke(0, 1)
                wineList.value = State.Success(data = getWineModel(responseList))
            } catch (e: Exception){
                wineList.value = State.Error(message = e.message.toString())
            }
        }

    }

    private fun getWineModel(list: List<WinePositionResponse>): List<WineModel> {
        val res = mutableListOf<WineModel>()
        for (response in list) {
            res.add(
                WineModel(
                    name = response.wine.name,
                    color = response.wine.color,
                    country = response.wine.region.first().country,//todo
                    amountOfSugar = response.wine.sugar,
                    volume = response.volume,
                    personalMatch = 1, //todo
                    rate = 2.0f, //todo
                    price = response.actualPrice,
                    discount =  2.0f,//todo
                    imageUrl = response.image,
                    tradeMarkUrl = response.wine.brand.name,
                    shop = response.shop.site,
                    year = response.wine.year,
                    sortOfGrape = response.wine.grape.first().name,//todo
                    isFavorite = true//todo
                )
            )
        }
        return res
    }

}