package com.itmo.wineup.features.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itmo.wineup.R
import com.itmo.wineup.features.favorites.presentation.models.FavoriteFilterModel
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import kotlinx.android.synthetic.main.fragment_sort.*


class SortFragment: BottomSheetDialogFragment() {

    private lateinit var viewModel: CatalogViewModel

    private val sort = mutableSetOf(FavoriteFilterModel.BY_RECOMMENDATION)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)


    }

    private fun setFilter(sorting: Set<FavoriteFilterModel>){
        for(sort in sorting)
            when(sort){
                FavoriteFilterModel.BY_RECOMMENDATION-> byRecommendCheckbox.isChecked = true
                FavoriteFilterModel.BY_RATING -> byRatingCheckbox.isChecked = true
                FavoriteFilterModel.BY_PRICE_MIN -> ascendingPriceCheckbox.isChecked = true
                FavoriteFilterModel.BY_PRICE_MAX -> descendingPriceCheckbox.isChecked = true
            }
    }

    private fun onCheckboxClicked() {
        if (byRecommendCheckbox.isChecked) {
            sort.add(FavoriteFilterModel.BY_RECOMMENDATION)
        } else {
            sort.remove(FavoriteFilterModel.BY_RECOMMENDATION)
        }

        if (byRatingCheckbox.isChecked) {
            sort.add(FavoriteFilterModel.BY_RATING)
        } else {
            sort.remove(FavoriteFilterModel.BY_RATING)
        }

        if (ascendingPriceCheckbox.isChecked) {
            sort.add(FavoriteFilterModel.BY_PRICE_MIN)
        } else {
            sort.remove(FavoriteFilterModel.BY_PRICE_MIN)
        }

        if (descendingPriceCheckbox.isChecked) {
            sort.add(FavoriteFilterModel.BY_PRICE_MAX)
        } else {
            sort.remove(FavoriteFilterModel.BY_PRICE_MAX)
        }

    }
}

