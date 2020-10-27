package com.itmo.wineup.features.catalog.presentation.filters

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.Recommendation
import com.itmo.wineup.features.catalog.presentation.CatalogFragment
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders.DismissListener
import com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders.Dismissable
import kotlinx.android.synthetic.main.fragment_filter_color.*
import kotlinx.android.synthetic.main.fragment_filter_recommend.*
import kotlinx.android.synthetic.main.fragment_filter_recommend.confirmButton

class FilterRecommendFragment : BottomSheetDialogFragment(), Dismissable {

    private lateinit var viewModel: CatalogViewModel
    private var dismissListener: DismissListener? = null

    override fun setOnDismissListener(listener: DismissListener) {
        dismissListener = listener
    }

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
        radio_group.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId) {
                R.id.recommended -> {
                    recommended.typeface = CatalogFragment.typeface_normal
                    by_rating.typeface = CatalogFragment.typeface_thin
                }
                R.id.by_rating -> {
                    recommended.typeface = CatalogFragment.typeface_thin
                    by_rating.typeface = CatalogFragment.typeface_normal
                }
            }
        }
        resetButton.setOnClickListener { viewModel.recommendationList.value = Recommendation.RECOMMENDED }
    }

    private fun setFilter(recommendation: Recommendation){
        when(recommendation){
            Recommendation.BY_RATING -> with(by_rating) {
                isChecked = true
                typeface = CatalogFragment.typeface_normal }
            Recommendation.RECOMMENDED -> with(recommended) {
                isChecked = true
                typeface = CatalogFragment.typeface_normal }
        }
    }

    private fun saveFilter() {
        viewModel.recommendationList.value = if (recommended.isChecked) Recommendation.RECOMMENDED else Recommendation.BY_RATING
    }
    override fun onPause() {
        saveFilter()
        super.onPause()
    }

    override fun onDismiss(dialog: DialogInterface) {
        dismissListener?.trigger()
        super.onDismiss(dialog)
    }
}