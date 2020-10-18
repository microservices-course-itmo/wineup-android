package com.itmo.wineup.features.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.presentation.adapters.WinesAdapter
import com.itmo.wineup.features.favorites.presentation.models.FavoriteFilterModel
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() =
            FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel

    private val adapter = WinesAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(FavoritesViewModel::class.java)
        viewModel.wineList.observe(viewLifecycleOwner, Observer(this::renderVineList))
        viewModel.selectedFilter.observe(viewLifecycleOwner, Observer(this::filterSelected))
        favoriteListRecycler.adapter = adapter
        viewModel.setWines()
        setListeners()
    }

    private fun renderVineList(vineList: List<WineModel>) {
        adapter.updateList(vineList)
    }

    private fun filterSelected(filter: FavoriteFilterModel) {
        Toast.makeText(context, "Selected filter: $filter", Toast.LENGTH_LONG).show()
    }

    private fun setListeners() {
        clear_favorite.setOnClickListener {
            //todo show alert
            adapter.updateList(emptyList())
            //todo: clear favorite list and show screen with "Тут пока пусто"
        }
        sort_by_button.setOnClickListener {
            SortFragment().show(
                requireActivity().supportFragmentManager,
                "BottomFavoriteSortFragment"
            )
        }


    }

}