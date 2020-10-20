package com.itmo.wineup.features.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.Recommendation
import com.itmo.wineup.features.favorites.presentation.models.FavoriteSortModel
import kotlinx.android.synthetic.main.fragment_filter_recommend.*
import kotlinx.android.synthetic.main.fragment_sort.*

class SortFragment: BottomSheetDialogFragment() {

    private lateinit var viewModel: FavoritesViewModel

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
        viewModel.selectedSort.observe(viewLifecycleOwner, Observer(this::setFilter))
    }

    private fun setFilter(sort: FavoriteSortModel){
            when(sort){
                FavoriteSortModel.BY_RECOMMENDATION -> byRecommendRadio.isChecked = true
                FavoriteSortModel.BY_RATING -> byRatingRadio.isChecked = true
                FavoriteSortModel.BY_PRICE_MIN -> ascendingPriceRadio.isChecked = true
                FavoriteSortModel.BY_PRICE_MAX -> descendingPriceRadio.isChecked = true
            }
    }

    override fun onPause() {
        viewModel.selectedSort.value = getSortModelFromRadio()
        super.onPause()
    }

    private fun getSortModelFromRadio() =
        when(sortRadioGroup.checkedRadioButtonId){
            byRatingRadio.id -> FavoriteSortModel.BY_RATING
            byRecommendRadio.id -> FavoriteSortModel.BY_RECOMMENDATION
            ascendingPriceRadio.id -> FavoriteSortModel.BY_PRICE_MIN
            descendingPriceRadio.id ->  FavoriteSortModel.BY_PRICE_MAX
            else -> FavoriteSortModel.BY_RECOMMENDATION
        }





}
