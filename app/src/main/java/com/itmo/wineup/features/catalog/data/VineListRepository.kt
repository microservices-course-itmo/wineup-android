package com.itmo.wineup.features.catalog.data

import com.itmo.wineup.features.catalog.models.VineModel

class VineListRepository {

    fun getList() = getHardCodedList()

    private fun getListFromApi(): List<VineModel> {
        //todo
        return emptyList()
    }

    private fun getHardCodedList() = listOf(
        //todo change when VineModel will be ready
        VineModel(name = "VineName1"),
        VineModel(name = "VineName2"),
        VineModel(name = "VineName3"),
        VineModel(name = "VineName4"),
        VineModel(name = "VineName5"),
        VineModel(name = "VineName6"),
        VineModel(name = "VineName7"),
        VineModel(name = "VineName8"),
        VineModel(name = "VineName9"),
        VineModel(name = "VineName10"),
        VineModel(name = "VineName11"),
        VineModel(name = "VineName12"),
        VineModel(name = "VineName13"),
        VineModel(name = "VineName14"),
        VineModel(name = "VineName15"),
        VineModel(name = "VineName16"),
        VineModel(name = "VineName17")
    )

}