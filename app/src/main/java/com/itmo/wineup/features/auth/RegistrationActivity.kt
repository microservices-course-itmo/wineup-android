package com.itmo.wineup.features.auth

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*


class RegistrationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var name: String
        var city: String

        enter_button.setOnClickListener(View.OnClickListener {
            name = name_input.toString()
            city = city_input.toString()
            if (name.length in 16 downTo 1){
                for (char in name){
                    if (char.isDigit()){
                        wrong_name.visibility = View.VISIBLE
                    }
                }
            }
        })
        date_input.setOnClickListener() {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val monthOfYear = monthOfYear + 1
                date_input.setText("$dayOfMonth.$monthOfYear.$year")
                //todo send Date to the next screen
            }
            val dpd = DatePickerDialog(this, dateSetListener, year, month, day).show()
        }
    }
}