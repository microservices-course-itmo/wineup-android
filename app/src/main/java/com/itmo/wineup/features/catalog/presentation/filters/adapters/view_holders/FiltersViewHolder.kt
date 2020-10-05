package com.itmo.wineup.features.catalog.presentation.filters.adapters.view_holders

import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
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

    private fun openFilterFragment(fragment: Fragment) {
        val transaction = (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.navHostFiltersFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}