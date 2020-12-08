package com.itmo.wineup.features.catalog.presentation.adapters

import android.util.Log
import androidx.paging.PagingSource
import com.itmo.wineup.features.catalog.data.WineListRepository
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.network.retrofit.data.WinePositionResponse
import java.lang.Exception
import kotlin.math.roundToInt

class CatalogPagingSource (private val repository: WineListRepository) : PagingSource<Int, WineModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WineModel> {
        try {
            val page = params.key ?: 0
            val result = repository.getList(page, 5)
            for (wine in result) {
                Log.d("Source", wine.toString())
            }
            return LoadResult.Page(
                toWineModel(result),
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (result.isEmpty()) null else page + 1
            )
        }
        catch (e : Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun toWineModel(list: List<WinePositionResponse>): List<WineModel> {
        val res = mutableListOf<WineModel>()
        for (response in list) {
            Log.d("Wine", response.wine.name)
            res.add(
                WineModel(
                    name = response.wine.name,
                    color = response.wine.color,
                    country = response.wine.region.firstOrNull()?.country ?: "",//todo
                    amountOfSugar = response.wine.sugar,
                    volume = response.volume,
                    personalMatch = 1, //todo
                    rate = 2.0f, //todo
                    price = response.actualPrice,
                    oldPrice = response.price,
                    discount =  (((response.price - response.actualPrice) / response.price) * 100).roundToInt(),//todo
                    imageUrl = response.image,
                    tradeMarkUrl = response.wine.brand.name,
                    shop = response.shop.site,
                    year = response.wine.year,
                    sortOfGrape = response.wine.grape.firstOrNull()?.name ?: "",//todo
                    gastronomy = response.gastronomy,
                    description = response.description,
                    isFavorite = true//todo
                )
            )
        }
        Log.d("List", res.toString())
        return res
    }

}