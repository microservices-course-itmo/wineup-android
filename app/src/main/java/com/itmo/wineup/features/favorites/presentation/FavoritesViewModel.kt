package com.itmo.wineup.features.favorites.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.favorites.presentation.models.FavoriteSortModel
import com.itmo.wineup.network.retrofit.RetrofitBuilder
import com.itmo.wineup.network.retrofit.data.WinePositionConverter
import com.itmo.wineup.network.retrofit.user.UserService
import kotlinx.coroutines.launch
import retrofit2.Callback

class FavoritesViewModel: ViewModel() {

    //todo: replace with getting favorite wines
    private val getWineListUseCase = GetWineListUseCase()

    val wineList = MutableLiveData<List<WineModel>>()
    val selectedSort = MutableLiveData<FavoriteSortModel>()


    fun setWines() {
        viewModelScope.launch {
            val favList = UserService.api().favoritesList()
            if (favList.isEmpty()) wineList.value = emptyList()
            else {
                val wines = RetrofitBuilder.wineApi.getFavoritesList(favList)
                wineList.value = WinePositionConverter.toWineModel(wines, true)
            }
        }
//        wineList.value = getWineListUseCase.getHardcodedList()
    }


}