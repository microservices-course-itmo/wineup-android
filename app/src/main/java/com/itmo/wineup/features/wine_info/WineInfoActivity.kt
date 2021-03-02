package com.itmo.wineup.features.wine_info

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.wine_activity.*
import com.itmo.wineup.features.catalog.domain.GetWineListUseCase
import com.itmo.wineup.features.catalog.models.WineModel
import com.itmo.wineup.features.catalog.presentation.adapters.WinesAdapter
import com.itmo.wineup.features.catalog.domain.GetFeedbackListUseCase
import com.itmo.wineup.features.catalog.models.FeedbackModel
import com.itmo.wineup.features.catalog.presentation.adapters.FeedbackAdapter
import kotlinx.android.synthetic.main.fragment_filter_price.*

class WineInfoActivity : AppCompatActivity() {
    companion object {
        const val WINE_MODEL_TAG = "WINE_MODEL"
        const val FEEDBACK_MODEL_TAG = "FEEDBACK_MODEL"
    }
    private var current: Int = 0
    private var currentFeedback : Int = 0

    private val getWineListUseCase = GetWineListUseCase()
    private lateinit var wineModel: WineModel
    private val adapter = WinesAdapter(mutableListOf())

    private val getFeedbackListUse = GetFeedbackListUseCase()
    private val feedbackList : List<FeedbackModel> = getFeedbackListUse.invoke()
    private var feedbackCurrentList : MutableList<FeedbackModel> = mutableListOf()
    private val adapterFeedback = FeedbackAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wine_activity)
        
        wineModel = intent.getSerializableExtra(WINE_MODEL_TAG) as WineModel
        populate()
        similarRecyclerView.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        similarRecyclerView.adapter = adapter
        similarRecyclerView.isNestedScrollingEnabled = false
        renderVineList(getWineListUseCase.getHardcodedList())
        button_back.alpha = 0.25F
        setSimilarListeners()

        feedbackRecyclerView.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        feedbackRecyclerView.adapter = adapterFeedback
        setFeedbackListeners()
        button_feedback.callOnClick()
        backButton.setOnClickListener { finish() }
    }

    private fun populate() {
        Glide.with(leftImage).load(wineModel.imageUrl).into(leftImage)
        productName.text = wineModel.name
        val volumeString = getString(R.string.wine_item_volume, wineModel.volume).replace(',', '.')
        productInfo.text = getString(R.string.wine_item_description, wineModel.country, wineModel.amountOfSugar, wineModel.color, volumeString)
        personalMatch.text = getString(R.string.wine_item_relevance, wineModel.personalMatch)
        if (wineModel.oldPrice == 0f || wineModel.oldPrice == wineModel.price) {
            discount.visibility = View.GONE
            oldPrice.visibility = View.GONE
        }
        else {
            oldPrice.text = getString(R.string.wine_item_old_price, wineModel.oldPrice)
            oldPrice.visibility = View.VISIBLE
            discount.visibility = View.VISIBLE
        }
        discount.text = getString(R.string.wine_item_discount, wineModel.discount)
        newPrice.text = getString(R.string.wine_item_price, wineModel.price)
        shop.text = wineModel.shop
        grapeName.text = wineModel.sortOfGrape
        year.text = getString(R.string.wine_item_year, wineModel.year)
        textAboutTaste.text = wineModel.description
        textAboutFood.text = wineModel.gastronomy
        if (wineModel.isFavorite) toFavorites.setImageResource(R.drawable.ic_like_red)
        else toFavorites.setImageResource(R.drawable.ic_like)
    }

    private fun renderFeedbackList(feedbackList: List<FeedbackModel>) {
        adapterFeedback.updateList(feedbackList)
    }

    private fun setFeedbackListeners(){
        button_feedback.setOnClickListener() {
            if (feedbackList.size - currentFeedback > 0) {
                feedbackCurrentList.add(feedbackList[currentFeedback])
                currentFeedback ++
                }
            if (feedbackList.size - currentFeedback > 0) {
                feedbackCurrentList.add(feedbackList[currentFeedback])
                currentFeedback ++
            }
            renderFeedbackList(feedbackCurrentList)
            button_feedback.isEnabled = currentFeedback != feedbackList.size
            if (currentFeedback == feedbackList.size) {
                button_feedback.visibility = View.INVISIBLE
            } else button_feedback.visibility = View.VISIBLE
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