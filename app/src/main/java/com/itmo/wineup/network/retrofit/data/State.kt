package com.itmo.wineup.network.retrofit.data

import com.itmo.wineup.features.catalog.models.WineModel

sealed class State {

    object Loading : State()

    class Success(val data: List<WineModel>) : State()

    class Error(val message: String) : State()

}