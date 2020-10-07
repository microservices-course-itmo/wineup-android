package com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.presentation.filters.*

class FiltersViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    private val filterName = item.findViewById<Button>(R.id.filterName)

    fun bind(model: String) {
        filterName.text = model
        filterName.setOnClickListener {
            when (model) {
                "Рекомендованные" -> openFilterFragment(FilterRecommendFragment())
                "Цена" -> openFilterFragment(FilterPriceFragment())
                "Страна" -> openFilterFragment(FilterCountriesFragment())
                "Цвет" -> openFilterFragment(FilterColorFragment())
                "Содержание сахара" -> openFilterFragment(FilterSugarFragment())
            }

        }
    }

    private fun openFilterFragment(fragment: BottomSheetDialogFragment) {
        fragment.show((itemView.context as AppCompatActivity).supportFragmentManager, fragment.tag)
    }
}