package com.itmo.wineup.features.catalog.presentation.filters

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.chip.Chip
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WinePriceFilter
import kotlinx.android.synthetic.main.activity_filter_price.*


class FilterPriceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_price)

        val minPriceEditText: EditText = min_price
        val maxPriceEditText: EditText = max_price
        var price: WinePriceFilter = WinePriceFilter(0,0,false)

        chips_group.setOnCheckedChangeListener{group,checkedId:Int ->
            when (checkedId){
                R.id.chip1 -> {
                    minPriceEditText.setText("0")
                    maxPriceEditText.setText("1500")
                }
                R.id.chip2 -> {
                    minPriceEditText.setText("1500")
                    maxPriceEditText.setText("3000")
                }
                R.id.chip3 -> {
                    minPriceEditText.setText("3000")
                    maxPriceEditText.setText("5000")
                }
                R.id.chip4 -> {
                    minPriceEditText.setText("5000")
                    maxPriceEditText.setText("10000")
                }
            }
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.noanimation)
    }

    fun onResetClick(view: View) {
        val minPriceEditText: EditText = min_price
        val maxPriceEditText: EditText = max_price
        min_price.text = null
        max_price.text = null
    }
    fun onConfirmClick(view: View) {
        val discountSwitch: SwitchCompat = discount_switch
        val minPriceEditText: EditText = min_price
        val maxPriceEditText: EditText = max_price

        var price: WinePriceFilter = WinePriceFilter(minPriceEditText.text.toString().toInt(),maxPriceEditText.text.toString().toInt(), discountSwitch.isChecked)
        finish()
    }

}