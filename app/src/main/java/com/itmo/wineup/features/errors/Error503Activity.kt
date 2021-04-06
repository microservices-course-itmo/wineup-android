package com.itmo.wineup.features.errors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.error_503.*

class Error503Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.error_503)
        Glide.with(error_503_background)
            .load(R.drawable.error_503_background)
            .into(error_503_background)
        withoutAuthButton.setOnClickListener {
            val loginIntent = Intent(applicationContext, MainActivity::class.java)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(loginIntent)
        }
        button_back.setOnClickListener {
            finish()
        }
    }
}