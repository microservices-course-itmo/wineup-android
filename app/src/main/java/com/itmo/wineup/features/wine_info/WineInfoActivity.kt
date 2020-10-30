package com.itmo.wineup.features.wine_info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineModel
import kotlinx.android.synthetic.main.wine_activity.*

class WineInfoActivity : AppCompatActivity() {

    companion object {
        const val WINE_MODEL_TAG = "WINE_MODEL"
    }

    private lateinit var wineModel: WineModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wine_activity)
        wineModel = intent.getSerializableExtra(WINE_MODEL_TAG) as WineModel
        wine_title.text = wineModel.toString()
    }

}