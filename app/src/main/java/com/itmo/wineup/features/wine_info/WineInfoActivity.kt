package com.itmo.wineup.features.wine_info

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.WineModel
import kotlinx.android.synthetic.main.wine_activity.*
import com.itmo.wineup.features.catalog.presentation.adapters.WinesAdapter

class WineInfoActivity : AppCompatActivity() {
    companion object {
        const val WINE_MODEL_TAG = "WINE_MODEL"
    }
    private var current: Int = 0
    private val getWineListUseCase = GetWineListUseCase()
    private lateinit var wineModel: WineModel
    private val adapter = WinesAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wine_activity)
        wineModel = intent.getSerializableExtra(WINE_MODEL_TAG) as WineModel
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        renderVineList(getWineListUseCase.invoke())
        setListeners()
        button_back.alpha = 0.25F

    }
    private fun renderVineList(vineList: List<WineModel>) {
        adapter.updateList(vineList)
    }

    private fun setListeners(){
        button_back.setOnClickListener() {
            current --
            button_back.isEnabled = current != 0
            if (current == 0) button_back.alpha = 0.25F else button_back.alpha = 1F
            button_forward.isEnabled = current != recyclerView.adapter?.itemCount ?: Int
            if (current == recyclerView.adapter?.itemCount ?: Int) button_forward.alpha = 0.25F else button_forward.alpha = 1F
            recyclerView.smoothScrollToPosition(current)
        }
        button_forward.setOnClickListener() {
            current ++
            button_back.isEnabled = current != 0
            if (current == 0) button_back.alpha = 0.25F else button_back.alpha = 1F
            button_forward.isEnabled = current != recyclerView.adapter?.itemCount ?: Int
            if (current == recyclerView.adapter?.itemCount ?: Int) button_forward.alpha = 0.25F else button_forward.alpha = 1F
            recyclerView.smoothScrollToPosition(current)
        }
    }
}