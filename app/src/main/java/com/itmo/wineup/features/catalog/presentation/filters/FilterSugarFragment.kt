package com.itmo.wineup.features.catalog.presentation.filters

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineSugar
import com.itmo.wineup.features.catalog.presentation.CatalogFragment
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders.DismissListener
import com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders.Dismissable
import kotlinx.android.synthetic.main.fragment_filter_sugar.*

class FilterSugarFragment : BottomSheetDialogFragment(), Dismissable{

    private lateinit var viewModel: CatalogViewModel
    private var dismissListener: DismissListener? = null

    override fun setOnDismissListener(listener: DismissListener) {
        dismissListener = listener
    }

    private val sugar = mutableSetOf<WineSugar>()

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
        sugar.addAll(viewModel.wineSugarList.value ?: emptyList())
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
        confirmButton.setOnClickListener{
            viewModel.wineSugarList.value = sugar
            dismiss()
        }
    }

    private fun setFilter(sugars: Set<WineSugar>){
        for(sugar in sugars)
            when(sugar){
                WineSugar.DRY-> with(dryWineCheckBox) {
                    isChecked = true
                    typeface = CatalogFragment.typeface_normal }
                WineSugar.MEDIUM_DRY -> with(semiDryWineCheckBox) {
                    isChecked = true
                    typeface = CatalogFragment.typeface_normal }
                WineSugar.MEDIUM -> with(semiSweetWineCheckBox) {
                    isChecked = true
                    typeface = CatalogFragment.typeface_normal }
                WineSugar.SWEET -> with(sweetWineCheckBox) {
                    isChecked = true
                    typeface = CatalogFragment.typeface_normal }
            }
    }

    private fun onCheckboxClicked() {
        if (dryWineCheckBox.isChecked) {
            sugar.add(WineSugar.DRY)
            dryWineCheckBox.typeface = CatalogFragment.typeface_normal
        } else {
            sugar.remove(WineSugar.DRY)
            dryWineCheckBox.typeface = CatalogFragment.typeface_thin
        }

        if (semiDryWineCheckBox.isChecked) {
            sugar.add(WineSugar.MEDIUM_DRY)
            semiDryWineCheckBox.typeface = CatalogFragment.typeface_normal
        } else {
            sugar.remove(WineSugar.MEDIUM_DRY)
            semiDryWineCheckBox.typeface = CatalogFragment.typeface_thin
        }

        if (semiSweetWineCheckBox.isChecked) {
            sugar.add(WineSugar.MEDIUM)
            semiSweetWineCheckBox.typeface = CatalogFragment.typeface_normal
        } else {
            sugar.remove(WineSugar.MEDIUM)
            semiSweetWineCheckBox.typeface = CatalogFragment.typeface_thin
        }

        if (sweetWineCheckBox.isChecked) {
            sugar.add(WineSugar.SWEET)
            sweetWineCheckBox.typeface = CatalogFragment.typeface_normal
        } else {
            sugar.remove(WineSugar.SWEET)
            sweetWineCheckBox.typeface = CatalogFragment.typeface_thin
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        dismissListener?.trigger()
        super.onDismiss(dialog)
    }

}