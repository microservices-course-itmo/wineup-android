package com.itmo.wineup.features.auth.presentation

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*


class RegistrationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var name: String
        var city: String
        val c = Calendar.getInstance()
        var currentYear = c.get(Calendar.YEAR)
        var currentMonth = c.get(Calendar.MONTH)
        var currentDay = c.get(Calendar.DAY_OF_MONTH)

        name_input.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                if (s==null || s.length < 2 || s.length > 15) {
                    wrong_name.visibility = View.VISIBLE
                    enter_button.enabled(false)
                } else {
                    wrong_name.visibility = View.INVISIBLE
                    enter_button.enabled(true)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        enter_button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        date_input.setOnClickListener() {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                date_input.setText("$dayOfMonth.${monthOfYear + 1}.$year")
                currentYear = year
                currentMonth = monthOfYear
                currentDay = dayOfMonth
                //todo send Date to the next screen
            }
            DatePickerDialog(this, dateSetListener, currentYear, currentMonth, currentDay).show()
        }
    }

    private fun Button.enabled(enabled: Boolean) {
        isEnabled = enabled
        setTextColor(ContextCompat.getColor(context, if (enabled) R.color.white else R.color.button_background_enabled))
        setBackgroundColor(
            ContextCompat.getColor(context, if (enabled) R.color.button_background_enabled else R.color.button_background_disabled)
        )
    }
}