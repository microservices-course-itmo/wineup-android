package com.itmo.wineup.features.catalog.presentation.filters

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.itmo.wineup.R

class FilterColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_color)
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.noanimation)
    }

    fun onCheckboxClicked(view: View) {}
}