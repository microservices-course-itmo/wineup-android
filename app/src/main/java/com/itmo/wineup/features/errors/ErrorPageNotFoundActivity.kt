package com.itmo.wineup.features.errors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.page_not_found.*

class ErrorPageNotFoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_not_found)
        Glide.with(error_background)
            .load(R.drawable.picture4)
            .into(error_background)
        withoutAuthButton.setOnClickListener {
            val loginIntent = Intent(applicationContext, MainActivity::class.java)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(loginIntent)
        }
        button.setOnClickListener {
            finish()
        }
    }
}