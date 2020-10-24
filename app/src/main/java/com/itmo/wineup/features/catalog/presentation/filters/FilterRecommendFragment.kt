package com.itmo.wineup.features.catalog.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.Recommendation
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import kotlinx.android.synthetic.main.fragment_filter_recommend.*

class FilterRecommendFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: CatalogViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_recommend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)
        viewModel.recommendationList.observe(viewLifecycleOwner, Observer(this::setFilter))
        confirmButton.setOnClickListener{ dismiss() }
    }

    private fun setFilter(recommendation: Recommendation){
        when(recommendation){
            Recommendation.BY_RATING -> by_rating.isChecked = true
            Recommendation.RECOMMENDED -> recommended.isChecked = true
        }
    }

    private fun saveFilter(){
        viewModel.recommendationList.value = if (recommended.isChecked) Recommendation.RECOMMENDED else Recommendation.BY_RATING
    }
    override fun onPause() {
        saveFilter()
        super.onPause()
    }
}