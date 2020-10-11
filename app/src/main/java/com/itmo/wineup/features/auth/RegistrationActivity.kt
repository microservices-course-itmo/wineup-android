package com.itmo.wineup.features.auth

import android.R.attr.button
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.jar.Attributes


class RegistrationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var name: String
        var city: String

        enter_button.setOnClickListener(View.OnClickListener {
            name = name_input.toString()
            city = city_input.toString()
        })
    }
}