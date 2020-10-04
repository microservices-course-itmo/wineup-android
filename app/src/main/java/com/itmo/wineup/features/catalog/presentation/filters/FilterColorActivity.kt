package com.itmo.wineup.features.catalog.presentation.filters

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineColor
import kotlinx.android.synthetic.main.activity_filter_color.*


class FilterColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_color)
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.noanimation)
    }
    private val colors = mutableListOf<WineColor>(WineColor.RED)
    fun onCheckboxClicked(view: View) {

        if (whiteWineCheckBox.isChecked) {
            if (WineColor.WHITE !in colors) colors.add(WineColor.WHITE)
        } else colors.remove(WineColor.WHITE)

        if (redWineCheckBox.isChecked) {
            if (WineColor.RED !in colors) colors.add(WineColor.RED)
        } else colors.remove(WineColor.RED)

        if (pinkWineCheckBox.isChecked) {
            if (WineColor.PINK !in colors) colors.add(WineColor.PINK)
        } else colors.remove(WineColor.PINK)

    }
    fun onResetClick(view: View) {
        whiteWineCheckBox.isChecked = false
        redWineCheckBox.isChecked = false
        pinkWineCheckBox.isChecked = false
        colors.clear()
    }

    fun onConfirmClick(view: View) {
//        TODO return result (colors)
        finish()
    }

}