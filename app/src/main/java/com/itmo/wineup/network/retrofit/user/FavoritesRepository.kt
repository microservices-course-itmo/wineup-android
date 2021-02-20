package com.itmo.wineup.network.retrofit.user

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object FavoritesRepository {

    fun addToFavorites(wineId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            UserService.api().addToFavorites(wineId)
        }
    }

    fun removeFromFavorites(wineId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            UserService.api().removeFromFavorites(wineId)
        }
    }

    fun clearFavorites() {
        CoroutineScope(Dispatchers.IO).launch {
            UserService.api().clearFavorites()
        }
    }

}