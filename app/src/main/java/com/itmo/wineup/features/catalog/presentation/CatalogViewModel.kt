package com.itmo.wineup.features.catalog.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itmo.wineup.network.retrofit.data.WineResponse
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.*
import com.itmo.wineup.network.retrofit.data.State
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
                val responseList = getWineListUseCase.invoke()
                wineList.value = State.Success(data = getWineModel(responseList))
            } catch (e: Exception){
                wineList.value = State.Error(message = e.message.toString())
            }
        }

    }

    private fun getWineModel(list: List<WineResponse>): List<WineModel> {
        val res = mutableListOf<WineModel>()
        for (response in list) {
            res.add(
                WineModel(
                    name = response.name,
                    color = response.color,
                    country = response.region.first().country,//todo
                    amountOfSugar = response.sugar,
                    volume = response.avg.toString(),
                    personalMatch = 1, //todo
                    rate = 2.0f, //todo
                    price = 1000, //todo
                    discount = 2, //todo
                    imageUrl = "url", //todo
                    tradeMarkUrl = "url", //todo
                    shop = response.producer.name,
                    year = response.year,
                    sortOfGrape = response.grape.first().name,//todo
                    isFavorite = true//todo
                )
            )
        }
        return res
    }

}