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
import com.itmo.wineup.features.catalog.models.WineSugar
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import kotlinx.android.synthetic.main.fragment_filter_sugar.*

class FilterSugarFragment : BottomSheetDialogFragment(){

    private lateinit var viewModel: CatalogViewModel

    private val sugar = mutableSetOf(WineSugar.DRY)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_sugar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)

        viewModel.wineSugarList.observe(viewLifecycleOwner, Observer(this::setFilter))
        resetButton.setOnClickListener {
            dryWineCheckBox.isChecked = false
            semiDryWineCheckBox.isChecked = false
            semiSweetWineCheckBox.isChecked = false
            sweetWineCheckBox.isChecked = false
            sugar.clear()
        }

        dryWineCheckBox.setOnClickListener { onCheckboxClicked() }
        semiDryWineCheckBox.setOnClickListener { onCheckboxClicked() }
        semiSweetWineCheckBox.setOnClickListener { onCheckboxClicked() }
        sweetWineCheckBox.setOnClickListener { onCheckboxClicked() }
    }

    override fun onPause() {
        viewModel.wineSugarList.value = sugar
        super.onPause()
    }

    private fun setFilter(sugars: Set<WineSugar>){
        for(sugar in sugars)
            when(sugar){
                WineSugar.DRY-> dryWineCheckBox.isChecked = true
                WineSugar.SEMI_DRY -> semiDryWineCheckBox.isChecked = true
                WineSugar.SEMI_SWEET -> semiSweetWineCheckBox.isChecked = true
                WineSugar.SWEET -> sweetWineCheckBox.isChecked = true
            }
    }

    private fun onCheckboxClicked() {
        if (dryWineCheckBox.isChecked) {
            sugar.add(WineSugar.DRY)
        } else {
            sugar.remove(WineSugar.DRY)
        }

        if (semiDryWineCheckBox.isChecked) {
            sugar.add(WineSugar.SEMI_DRY)
        } else {
            sugar.remove(WineSugar.SEMI_DRY)
        }

        if (semiSweetWineCheckBox.isChecked) {
            sugar.add(WineSugar.SEMI_SWEET)
        } else {
            sugar.remove(WineSugar.SEMI_SWEET)
        }

        if (sweetWineCheckBox.isChecked) {
            sugar.add(WineSugar.SWEET)
        } else {
            sugar.remove(WineSugar.SWEET)
        }

    }

}