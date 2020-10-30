package com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itmo.wineup.features.catalog.presentation.filters.*
import kotlinx.android.synthetic.main.item_filter.view.*

class FiltersViewHolder(item: View) : RecyclerView.ViewHolder(item), DismissListener {

    private val filterName = item.chip

    fun bind(model: String) {
        filterName.text = model
        filterName.isChecked = false
        filterName.setOnClickListener {
            filterName.isChecked = true
            var fragment: BottomSheetDialogFragment? = when (model) {
                "Рекомендованные" -> FilterRecommendFragment()
                "Цена" -> FilterPriceFragment()
                "Страна" -> FilterCountriesFragment()
                "Цвет" -> FilterColorFragment()
                "Содержание сахара" -> FilterSugarFragment()
                else -> null
            }
            if (fragment != null) {
                (fragment as Dismissable).setOnDismissListener(this)
                openFilterFragment(fragment)
            }
        }
    }

    override fun trigger() {
        filterName.isChecked = false
    }

    private fun openFilterFragment(fragment: BottomSheetDialogFragment) {
        fragment.show((itemView.context as AppCompatActivity).supportFragmentManager, fragment.tag)
    }
}

interface DismissListener {
    fun trigger()
}

interface Dismissable {
    fun setOnDismissListener(listener: DismissListener)
}