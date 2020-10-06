package com.itmo.wineup.features.catalog.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineSugar
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import kotlinx.android.synthetic.main.activity_filter_sugar.*

class FilterSugarFragment : Fragment() {

    private lateinit var viewModel: CatalogViewModel

    private val sugar = mutableSetOf(WineSugar.DRY)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_filter_sugar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)

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