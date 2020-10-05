package com.itmo.wineup.features.catalog.presentation.filters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineSugar
import kotlinx.android.synthetic.main.activity_filter_sugar.*

class FilterSugarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_sugar)
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.noanimation)
    }
    private val sugar = mutableListOf<WineSugar>(WineSugar.DRY)
    fun onCheckboxClicked(view: View) {

        if (dryWineCheckBox.isChecked) {
            if (WineSugar.DRY !in sugar) sugar.add(WineSugar.DRY)
        } else sugar.remove(WineSugar.DRY)

        if (semiDryWineCheckBox.isChecked) {
            if (WineSugar.SEMI_DRY !in sugar) sugar.add(WineSugar.SEMI_DRY)
        } else sugar.remove(WineSugar.SEMI_DRY)

        if (semiSweetWineCheckBox.isChecked) {
            if (WineSugar.SEMI_SWEET !in sugar) sugar.add(WineSugar.SEMI_SWEET)
        } else sugar.remove(WineSugar.SEMI_SWEET)

        if (sweetWineCheckBox.isChecked) {
            if (WineSugar.SWEET !in sugar) sugar.add(WineSugar.SWEET)
        } else sugar.remove(WineSugar.SWEET)

    }
    fun onResetClick(view: View) {
        dryWineCheckBox.isChecked = false
        semiDryWineCheckBox.isChecked = false
        semiSweetWineCheckBox.isChecked = false
        sweetWineCheckBox.isChecked = false
        sugar.clear()
    }

}