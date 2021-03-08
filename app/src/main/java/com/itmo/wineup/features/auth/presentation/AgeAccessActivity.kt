package com.itmo.wineup.features.auth.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import com.itmo.wineup.features.auth.USER_ACCESS_INFO
import kotlinx.android.synthetic.main.activity_age_access.*

class AgeAccessActivity : AppCompatActivity() {

    private lateinit var viewModel : AgeAccessViewModel
    private val autoLogin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_access)
        viewModel = ViewModelProvider(this).get(AgeAccessViewModel::class.java)
        buttonYes.setOnClickListener {
            val intent = Intent(this, PhoneNumberAccessActivity::class.java)
            startActivity(intent)
        }
        buttonNo.setOnClickListener{
            val intent = Intent(this, AgeAccessErrorActivity::class.java)
            startActivity(intent)
        }
        viewModel.accessStateLiveData.observe(this, Observer {
            if (it == AgeAccessViewModel.AccessState.GRANTED) {
                val loginIntent = Intent(applicationContext, MainActivity::class.java)
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(loginIntent)
            }
        })
        tryLogin()
    }

    private fun tryLogin() {
        if (!autoLogin)  {
            viewModel.skipLogin()
            return
        }
        viewModel.preferences = getSharedPreferences(USER_ACCESS_INFO, Context.MODE_PRIVATE)
        viewModel.login()

    }
}