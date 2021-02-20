package com.itmo.wineup.features.catalog.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.itmo.wineup.features.catalog.data.WineListRepository
import com.itmo.wineup.network.retrofit.data.WineResponse
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.*
import com.itmo.wineup.features.catalog.presentation.adapters.CatalogPagingSource
import com.itmo.wineup.network.retrofit.RetrofitBuilder
import com.itmo.wineup.network.retrofit.data.State
import com.itmo.wineup.network.retrofit.data.WinePositionResponse
import kotlinx.coroutines.launch

class CatalogViewModel : ViewModel() {

    private val getWineListUseCase = GetWineListUseCase()

    val wineList = MutableLiveData<State>()
    val wineColorList = MutableLiveData<Set<WineColor>>()
    val wineSugarList = MutableLiveData<Set<WineSugar>>()
    val countriesList = MutableLiveData<List<String>>()
    val recommendationList = MutableLiveData(Recommendation.RECOMMENDED)
    val priceValue = MutableLiveData<WinePriceFilter>()
    val query = MutableLiveData("")
    private var currentSource: CatalogPagingSource? = null
    val pager = Pager(config = PagingConfig(1, enablePlaceholders = true, maxSize = 100))
    { sourceFactory() }.flow.cachedIn(viewModelScope)


//    fun setWines() {
//        wineList.value = State.Loading
//        viewModelScope.launch {
//            try {
//                val responseList = getWineListUseCase.invoke(0, 16)
//                wineList.value = State.Success(data = getWineModel(responseList))
//            } catch (e: Exception){
//                wineList.value = State.Error(message = e.message.toString())
//            }
//        }
//
//    }

    fun updateCatalog() = currentSource?.invalidate()

    private fun sourceFactory() : PagingSource<Int, WineModel> {
        val sugar = wineSugarList.value?.ifEmpty { null }?.joinToString(separator = ";~", postfix = ";") { "sugar:${it.name}" } ?: ""
        val color = wineColorList.value?.ifEmpty { null }?.joinToString(separator = ";~", postfix = ";") { "color:${it.name}" } ?: ""
        val price = priceValue.value?.let {
            if (it.minPrice != null && it.maxPrice != null) "actual_price>${it.minPrice};*actual_price<${it.maxPrice};"
            else if (it.minPrice != null) "actual_price>${it.minPrice};"
            else if (it.maxPrice != null) "actual_price<${it.maxPrice};"
            else ""
        } ?: ""
        val brand = query.value?.let {
            if (it.isNotEmpty()) "brandName:$it"
            else ""
        } ?: ""
        val params = sugar+color+price+brand
        Log.d("Params", params)
        return CatalogPagingSource(WineListRepository(RetrofitBuilder.wineApi, params)).also { currentSource = it }
    }

}