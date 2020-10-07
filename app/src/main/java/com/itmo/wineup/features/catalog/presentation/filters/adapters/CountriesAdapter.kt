package com.itmo.wineup.features.catalog.presentation.filters.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.presentation.filters.adapters.viewholders.CountryViewHolder
import com.itmo.wineup.features.catalog.presentation.filters.models.CountryModel
import java.util.*

class CountriesAdapter : RecyclerView.Adapter<CountryViewHolder>(), Filterable {

    private val countries = mutableListOf<CountryModel>()
    private val filteredResults = mutableListOf<CountryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false))

    override fun getItemCount(): Int = filteredResults.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(filteredResults[position])
    }

    override fun getFilter(): Filter = countryFilter

    fun setData(data: List<String>, restore: List<String>?) {
        countries.clear()
        for (country in data) {
            countries.add(CountryModel(country, restore?.contains(country) ?: false))
        }
        filteredResults.clear()
        for (country in countries) {
            filteredResults.add(country)
        }
        notifyDataSetChanged()
    }


    fun getCheckedCountries() : List<String> {
        val result = mutableListOf<String>()
        for (model in countries) {
            if (model.isChecked) result.add(model.name)
        }
        return result
    }

    fun reset() {
        for (model in countries) {
            model.isChecked = false
        }
        notifyDataSetChanged()
    }

    private val countryFilter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = mutableListOf<CountryModel>()
            if (constraint == null || constraint.isEmpty()) {
                results.addAll(countries)
            }
            else {
                val pattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                for (country in countries) {
                    if (country.name.toLowerCase(Locale.ROOT).contains(pattern)) results.add(country)
                }
            }
            return FilterResults().apply { values = results }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredResults.clear()
            filteredResults.addAll(results?.values as List<CountryModel>)
            notifyDataSetChanged()
        }

    }

}