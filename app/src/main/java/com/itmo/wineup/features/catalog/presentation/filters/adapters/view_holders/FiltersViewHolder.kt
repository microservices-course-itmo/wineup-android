package com.itmo.wineup.features.catalog.presentation.filters.adapters.view_holders

import android.view.View
import android.widget.Button
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R

class FiltersViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

    private val filterName = item.findViewById<Button>(R.id.filterName)

    fun bind(model: String) {
        filterName.text = model
        filterName.setOnClickListener {
            when (model) {
                "Рекомендованные" -> findNavController(item.findFragment()).navigate(R.id.filerRecommendActivity)
                "Цена" -> findNavController(item.findFragment()).navigate(R.id.filterPriceActivity)
                "Страна" -> findNavController(item.findFragment()).navigate(R.id.filterCountriesActivity)
                "Цвет" -> findNavController(item.findFragment()).navigate(R.id.filterColorActivity)
                "Содержание сахара" -> findNavController(item.findFragment()).navigate(R.id.filterSugarActivity)
            }

        }
    }
}