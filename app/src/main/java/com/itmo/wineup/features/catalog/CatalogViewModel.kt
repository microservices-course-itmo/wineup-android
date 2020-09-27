package com.itmo.wineup.features.catalog

import androidx.lifecycle.ViewModel

class CatalogViewModel: ViewModel() {
    fun addWines(): ArrayList<WineModel> {
        val wines = arrayListOf<WineModel>()
        for (i in 0..9){
            wines.add(WineModel("Canti Merlot", "Франция", "красное", "сухое",
                "0.75л", 85, 4.5F, 2300, 15))
        }
        return wines
    }
}