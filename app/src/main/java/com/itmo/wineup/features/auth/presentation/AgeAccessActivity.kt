package com.itmo.wineup.features.auth.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_age_access.*

class AgeAccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_access)
        buttonYes.setOnClickListener {
            val intent = Intent(this, CodeInputActivity::class.java)
            startActivity(intent)
        }
        buttonNo.setOnClickListener{
            val intent = Intent(this, AgeAccessErrorActivity::class.java)
            startActivity(intent)
        }
    }
}