package com.itmo.wineup.features.catalog.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineColor
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import kotlinx.android.synthetic.main.activity_filter_color.*

class FilterColorFragment : Fragment() {

    private lateinit var viewModel: CatalogViewModel

    private val colors = mutableSetOf(WineColor.RED)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_filter_color, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)

        reset_button.setOnClickListener {
            whiteWineCheckBox.isChecked = false
            redWineCheckBox.isChecked = false
            pinkWineCheckBox.isChecked = false
            colors.clear()
        }
        whiteWineCheckBox.setOnClickListener { onCheckboxClicked() }
        redWineCheckBox.setOnClickListener { onCheckboxClicked() }
        pinkWineCheckBox.setOnClickListener { onCheckboxClicked() }

    }

    override fun onPause() {
        viewModel.wineColorList.value = colors
        super.onPause()
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