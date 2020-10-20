package com.itmo.wineup.features.favorites.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.presentation.CatalogFragment
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

        favoriteListRecycler.addItemDecoration(
            DividerItemDecoration(
                favoriteListRecycler.context,
                LinearLayout.VERTICAL
            ).apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.divider)
                    ?.let { setDrawable(it) }
            }
        )
        
        viewModel.setWines()
        setListeners()
    }

    private fun renderVineList(vineList: List<WineModel>) {
        adapter.updateList(vineList)
        favorites_empty_container.visibility = if (vineList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun filterSelected(filter: FavoriteFilterModel) {
        Toast.makeText(context, "Selected filter: $filter", Toast.LENGTH_LONG).show()
    }

    private fun setListeners() {
        clear_favorite.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.clear_alert_title)
                .setPositiveButton(R.string.yes) { _, _ ->
                    //todo: clear favorite list
                    viewModel.wineList.value = emptyList()
                }
                .setNegativeButton(R.string.no) { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                .show()
        }
        sort_by_button.setOnClickListener {
            SortFragment().show(
                requireActivity().supportFragmentManager,
                "BottomFavoriteSortFragment"
            )
        }
        to_catalog_button.setOnClickListener {
            findNavController().navigate(R.id.navigation_catalog)
        }
        searchInFavourites.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) hideNothingFoundScreen()
                else showNothingFoundScreen()
                return false
            }
        })
    }

    private fun showNothingFoundScreen() {
        nothing_found_container.visibility = View.VISIBLE
    }

    private fun hideNothingFoundScreen() {
        nothing_found_container.visibility = View.GONE
    }

}