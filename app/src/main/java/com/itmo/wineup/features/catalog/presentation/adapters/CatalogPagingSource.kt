package com.itmo.wineup.features.catalog.presentation.adapters

import android.util.Log
import androidx.paging.PagingSource
import com.itmo.wineup.features.catalog.data.WineListRepository
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.network.retrofit.data.WinePositionConverter
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
                WinePositionConverter.toWineModel(result),
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (result.isEmpty()) null else page + 1
            )
        }
        catch (e : Exception) {
            return LoadResult.Error(e)
        }
    }

}