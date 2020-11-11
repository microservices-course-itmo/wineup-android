package com.itmo.wineup.features.wine_info

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.Recommendation
import kotlinx.android.synthetic.main.wine_activity.*
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.presentation.adapters.WinesAdapter

class WineInfoActivity : AppCompatActivity() {
    companion object {
        const val WINE_MODEL_TAG = "WINE_MODEL"
    }
    private var current: Int = 0
    private lateinit var recommendation: Recommendation
    private val getWineListUseCase = GetWineListUseCase()
    private lateinit var wineModel: WineModel
    private val adapter = WinesAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wine_activity)
        
        wineModel = intent.getSerializableExtra(WINE_MODEL_TAG) as WineModel
        similarRecyclerView.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        similarRecyclerView.adapter = adapter
        similarRecyclerView.isNestedScrollingEnabled = false
        renderVineList(getWineListUseCase.getHardcodedList())
        button_back.alpha = 0.25F
        setSimilarListeners()
    }


    private fun setFeedbackListeners(){
        button_feedback.setOnClickListener() {
            current ++
            button_feedback.isEnabled = current != 0
            if (current == 0) {
                button_feedback.visibility = View.INVISIBLE
            } else button_feedback.visibility = View.VISIBLE
            button_feedback.isEnabled = current != feedbackRecyclerView.adapter?.itemCount ?: Int
            if (current == feedbackRecyclerView.adapter?.itemCount ?: Int) {
                button_feedback.visibility = View.INVISIBLE
            } else button_feedback.visibility = View.VISIBLE
            feedbackRecyclerView.smoothScrollToPosition(current)
        }

    }
    private fun renderVineList(vineList: List<WineModel>) {
        adapter.updateList(vineList)
    }

    private fun setSimilarListeners(){
        button_back.setOnClickListener() {
            current --
            button_back.isEnabled = current != 0
            if (current == 0) button_back.alpha = 0.25F else button_back.alpha = 1F
            button_forward.isEnabled = current != similarRecyclerView.adapter?.itemCount ?: Int
            if (current == similarRecyclerView.adapter?.itemCount ?: Int) button_forward.alpha = 0.25F else button_forward.alpha = 1F
            similarRecyclerView.smoothScrollToPosition(current)
        }
        button_forward.setOnClickListener() {
            current ++
            button_back.isEnabled = current != 0
            if (current == 0) button_back.alpha = 0.25F else button_back.alpha = 1F
            button_forward.isEnabled = current != similarRecyclerView.adapter?.itemCount ?: Int
            if (current == similarRecyclerView.adapter?.itemCount ?: Int) button_forward.alpha = 0.25F else button_forward.alpha = 1F
            similarRecyclerView.smoothScrollToPosition(current)
        }
    }
}