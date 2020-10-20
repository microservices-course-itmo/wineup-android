package com.itmo.wineup.features.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itmo.wineup.R
import com.itmo.wineup.features.favorites.presentation.models.FavoriteFilterModel
import kotlinx.android.synthetic.main.fragment_filter_recommend.by_rating
import kotlinx.android.synthetic.main.fragment_sort.*

class SortFragment: BottomSheetDialogFragment() {

    private val sort = mutableSetOf(FavoriteFilterModel.BY_RECOMMENDATION)
    private lateinit var viewModel: FavoritesViewModel
    private var closeFragment = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FavoritesViewModel::class.java)
        viewModel.selectedFilter.observe(viewLifecycleOwner, Observer(this::setFilter))
        setListener()
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
       

    private fun setFilter(selectedValue: FavoriteFilterModel) {
        when (selectedValue) {
            FavoriteFilterModel.BY_RECOMMENDATION -> by_recommendation.isChecked = true
            FavoriteFilterModel.BY_RATING -> by_rating.isChecked = true
            FavoriteFilterModel.BY_PRICE_MIN -> by_price_min.isChecked = true
            FavoriteFilterModel.BY_PRICE_MAX -> by_price_max.isChecked = true
        }
        closeFragment = true
    }

    private fun setListener() {
        filters_radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.by_recommendation -> viewModel.selectedFilter.value = FavoriteFilterModel.BY_RECOMMENDATION
                R.id.by_rating -> viewModel.selectedFilter.value = FavoriteFilterModel.BY_RATING
                R.id.by_price_min -> viewModel.selectedFilter.value = FavoriteFilterModel.BY_PRICE_MIN
                R.id.by_price_max -> viewModel.selectedFilter.value = FavoriteFilterModel.BY_PRICE_MAX
            }
            if (closeFragment) dismiss()
        }
    }

}
