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
import com.itmo.wineup.features.catalog.models.WinePriceFilter
import com.itmo.wineup.features.catalog.presentation.CatalogViewModel
import kotlinx.android.synthetic.main.fragment_filter_price.*

class FilterPriceFragment : BottomSheetDialogFragment(){

    private lateinit var viewModel: CatalogViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_price, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CatalogViewModel::class.java)
        viewModel.priceValue.observe(viewLifecycleOwner, Observer(this::setFilter))
        reset_button.setOnClickListener {
            min_price.text = null
            max_price.text = null
        }
        confirm_button.setOnClickListener { confirmClick() }

        chips_group.setOnCheckedChangeListener { group, checkedId: Int ->
            when (checkedId) {
                R.id.chip1 -> {
                    min_price.setText("0")
                    max_price.setText("1500")
                }
                R.id.chip2 -> {
                    min_price.setText("1500")
                    max_price.setText("3000")
                }
                R.id.chip3 -> {
                    min_price.setText("3000")
                    max_price.setText("5000")
                }
                R.id.chip4 -> {
                    min_price.setText("5000")
                    max_price.setText("10000")
                }
            }
        }
    }

    private fun setFilter(winePriceFilter: WinePriceFilter){
        min_price.setText(winePriceFilter.minPrice?.toString() ?: "")
        max_price.setText(winePriceFilter.maxPrice?.toString() ?: "")
        discount_switch.isChecked = winePriceFilter.itemWithDiscount
    }
    private fun confirmClick() {
       viewModel.priceValue.value = WinePriceFilter(
            min_price.text.trim().toString().takeIf {  it.isNotBlank()}?.toInt(),
            max_price.text.trim().toString().takeIf { it.isNotBlank() }?.toInt(),
            discount_switch.isChecked
        )
        dismiss()
    }
}

