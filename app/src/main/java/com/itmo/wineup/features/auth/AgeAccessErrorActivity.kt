package com.itmo.wineup.features.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_age_access_error.*

class AgeAccessErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_access_error)
        buttonBack.setOnClickListener{
            onBackPressed()
        }
    }
}