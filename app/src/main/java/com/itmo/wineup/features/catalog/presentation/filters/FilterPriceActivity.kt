package com.itmo.wineup.features.catalog.presentation.filters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itmo.wineup.R

class FilterPriceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_price)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.noanimation)
    }

}