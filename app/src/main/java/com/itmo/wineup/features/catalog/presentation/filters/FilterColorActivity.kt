package com.itmo.wineup.features.catalog.presentation.filters

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.annotation.Nullable
import com.itmo.wineup.R
import com.itmo.wineup.features.catalog.models.WineColor

class FilterColorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_color)
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.noanimation)
    }
    public val colors = mutableListOf<WineColor>(WineColor.RED, WineColor.PINK, WineColor.WHITE)
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            when (view.id) {
                R.id.whiteWineCheckBox -> {
                    if (checked) {
                        if (WineColor.WHITE !in colors) {
                            colors.add(WineColor.WHITE)
                        }
                    } else {
                        if (WineColor.WHITE in colors) {
                            colors.remove(WineColor.WHITE)
                        }
                    }
                }
                R.id.redWineCheckBox -> {
                    if (checked) {
                        if (WineColor.RED !in colors) {
                            colors.add(WineColor.RED)
                        }
                    } else {
                        if (WineColor.RED in colors) {
                            colors.remove(WineColor.RED)
                        }
                    }
                }
                R.id.pinkWineCheckBox -> {
                    if (checked) {
                        if (WineColor.PINK !in colors) {
                            colors.add(WineColor.PINK)
                        }
                    } else {
                        if (WineColor.PINK in colors) {
                            colors.remove(WineColor.PINK)
                        }
                    }
                }
            }
        }

    }
}