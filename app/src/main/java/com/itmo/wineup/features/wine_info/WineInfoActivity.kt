package com.itmo.wineup.features.wine_info

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.Recommendation
import kotlinx.android.synthetic.main.wine_activity.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.presentation.adapters.WinesAdapter

class WineInfoActivity : AppCompatActivity() {

    companion object {
        const val WINE_MODEL_TAG = "WINE_MODEL"
    }

    private var current: Int = 0
    private lateinit var recommendation: Recommendation
    private val getWineListUseCase = GetWineListUseCase()
    private val adapter = WinesAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wine_activity)

        recommendation = intent.getSerializableExtra(WINE_MODEL_TAG) as Recommendation
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter
        renderRecommendationList(getWineListUseCase.invoke())
        setListeners()
    }

    private fun renderRecommendationList(recommendationList: List<Recommendation>) {
        adapter.updateList(recommendationList)

    }

    private fun setListeners(){
        button_feedback.setOnClickListener() {
            current ++
            button_feedback.isEnabled = current != 0
            if (current == 0) {
                button_feedback.visibility = View.INVISIBLE
            } else button_feedback.visibility = View.VISIBLE
            button_feedback.isEnabled = current != recyclerView.adapter?.itemCount ?: Int
            if (current == recyclerView.adapter?.itemCount ?: Int) {
                button_feedback.visibility = View.INVISIBLE
            } else button_feedback.visibility = View.VISIBLE
            recyclerView.smoothScrollToPosition(current)
        }

    }


}