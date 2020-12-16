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
import com.itmo.wineup.features.catalog.models.WineColor
import com.itmo.wineup.features.catalog.presentation.CatalogFragment
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders.DismissListener
import com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders.Dismissable
import kotlinx.android.synthetic.main.fragment_filter_color.*
import kotlinx.android.synthetic.main.fragment_filter_color.confirmButton

class FilterColorFragment : BottomSheetDialogFragment(), Dismissable{

    private lateinit var viewModel: CatalogViewModel
    private var dismissListener: DismissListener? = null

    override fun setOnDismissListener(listener: DismissListener) {
        dismissListener = listener
    }

    private val colors = mutableSetOf<WineColor>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_color, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)

        colors.addAll(viewModel.wineColorList.value ?: emptyList())
        viewModel.wineColorList.observe(viewLifecycleOwner, Observer(this::setFilter))
        reset_button.setOnClickListener {
            whiteWineCheckBox.isChecked = false
            redWineCheckBox.isChecked = false
            pinkWineCheckBox.isChecked = false
            colors.clear()
            viewModel.wineColorList.value = colors
        }
        whiteWineCheckBox.setOnClickListener { onCheckboxClicked() }
        redWineCheckBox.setOnClickListener { onCheckboxClicked() }
        pinkWineCheckBox.setOnClickListener { onCheckboxClicked() }
        confirmButton.setOnClickListener {
            viewModel.wineColorList.value = colors
            dismiss()
        }
    }
    private fun setFilter(colors: Set<WineColor>){
        for(color in colors)
            when(color){
                WineColor.ROSE-> with(pinkWineCheckBox) {
                    isChecked = true
                    typeface = CatalogFragment.typeface_normal }
                WineColor.RED -> with(redWineCheckBox) {
                    isChecked = true
                    typeface = CatalogFragment.typeface_normal }
                WineColor.WHITE -> with(whiteWineCheckBox) {
                    isChecked = true
                    typeface = CatalogFragment.typeface_normal }
            }
    }




    private fun onCheckboxClicked() {
        if (whiteWineCheckBox.isChecked) {
            colors.add(WineColor.WHITE)
            whiteWineCheckBox.typeface = CatalogFragment.typeface_normal
        } else {
            colors.remove(WineColor.WHITE)
            whiteWineCheckBox.typeface = CatalogFragment.typeface_thin
        }

        if (redWineCheckBox.isChecked) {
            colors.add(WineColor.RED)
            redWineCheckBox.typeface = CatalogFragment.typeface_normal
        } else {
            colors.remove(WineColor.RED)
            redWineCheckBox.typeface = CatalogFragment.typeface_thin
        }

        if (pinkWineCheckBox.isChecked) {
            colors.add(WineColor.ROSE)
            pinkWineCheckBox.typeface = CatalogFragment.typeface_normal
        } else {
            colors.remove(WineColor.ROSE)
            pinkWineCheckBox.typeface = CatalogFragment.typeface_thin
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        dismissListener?.trigger()
        super.onDismiss(dialog)
    }

}