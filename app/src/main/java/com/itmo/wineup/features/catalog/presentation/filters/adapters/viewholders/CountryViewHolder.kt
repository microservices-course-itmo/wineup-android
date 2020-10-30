package com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.features.catalog.presentation.CatalogFragment
import com.itmo.wineup.features.catalog.presentation.filters.models.CountryModel
import kotlinx.android.synthetic.main.item_country.view.*

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val checkbox = itemView.country_checkbox

    fun bind(country: CountryModel) {
        checkbox.text = country.name
        checkbox.isChecked = country.isChecked
        setStyle(country.isChecked)
        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                country.isChecked = isChecked
                setStyle(isChecked)
            }
        }
    }

    private fun setStyle(isChecked: Boolean) {
        if (isChecked)
            with(itemView.country_checkbox) {
                typeface = CatalogFragment.typeface_normal
            }
        else
            with(itemView.country_checkbox) {
                typeface = CatalogFragment.typeface_thin
            }
    }
}