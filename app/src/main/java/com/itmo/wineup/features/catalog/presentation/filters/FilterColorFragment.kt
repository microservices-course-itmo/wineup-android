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
import com.itmo.wineup.features.catalog.models.WineColor
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import kotlinx.android.synthetic.main.fragment_filter_color.*
import kotlinx.android.synthetic.main.fragment_filter_recommend.*

class FilterColorFragment : BottomSheetDialogFragment(){

    private lateinit var viewModel: CatalogViewModel

    private val colors = mutableSetOf(WineColor.RED)

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


        viewModel.wineColorList.observe(viewLifecycleOwner, Observer(this::setFilter))
        reset_button.setOnClickListener {
            whiteWineCheckBox.isChecked = false
            redWineCheckBox.isChecked = false
            pinkWineCheckBox.isChecked = false
            colors.clear()
        }
        whiteWineCheckBox.setOnClickListener { onCheckboxClicked() }
        redWineCheckBox.setOnClickListener { onCheckboxClicked() }
        pinkWineCheckBox.setOnClickListener { onCheckboxClicked() }
        confirm_button.setOnClickListener {
            viewModel.wineColorList.value = colors
            dismiss()
        }
    }
    private fun setFilter(colors: Set<WineColor>){
        for(color in colors)
            when(color){
                WineColor.PINK-> pinkWineCheckBox.isChecked = true
                WineColor.RED -> redWineCheckBox.isChecked = true
                WineColor.WHITE -> whiteWineCheckBox.isChecked = true
            }
    }




    private fun onCheckboxClicked() {
        if (whiteWineCheckBox.isChecked) {
            colors.add(WineColor.WHITE)
        } else {
            colors.remove(WineColor.WHITE)
        }

        if (redWineCheckBox.isChecked) {
            colors.add(WineColor.RED)
        } else {
            colors.remove(WineColor.RED)
        }

        if (pinkWineCheckBox.isChecked) {
            colors.add(WineColor.PINK)
        } else {
            colors.remove(WineColor.PINK)
        }

    }

}