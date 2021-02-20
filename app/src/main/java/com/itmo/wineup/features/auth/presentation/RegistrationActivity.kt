package com.itmo.wineup.features.auth.presentation

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import com.itmo.wineup.features.auth.USER_ACCESS_INFO
import com.itmo.wineup.features.auth.USER_ACCESS_TOKEN
import com.itmo.wineup.features.auth.USER_CURRENT_ID
import com.itmo.wineup.features.auth.USER_REFRESH_TOKEN
import com.itmo.wineup.features.auth.data.UserRepository
import com.itmo.wineup.network.retrofit.user.LoginResponse
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class RegistrationActivity: AppCompatActivity() {

    companion object {
        const val EXTRA_FIREBASE_TOKEN = "FirebaseToken"
    }

    private var nameIsValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val c = Calendar.getInstance()
        var currentYear = c.get(Calendar.YEAR)
        var currentMonth = c.get(Calendar.MONTH)
        var currentDay = c.get(Calendar.DAY_OF_MONTH)

        city_input.adapter = ArrayAdapter.createFromResource(this, R.array.cities_list, R.layout.support_simple_spinner_dropdown_item)

        name_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s==null || s.length < 2 || s.length > 15) {
                    wrong_name.visibility = View.VISIBLE
                    nameIsValid = false
                } else {
                    wrong_name.visibility = View.INVISIBLE
                    nameIsValid = true
                }
                updateButtonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) { updateButtonState() }
        }
        date_input.addTextChangedListener(watcher)
        materialCheckBox.setOnCheckedChangeListener { _, _ -> updateButtonState() }
        enter_button.setOnClickListener {
            enter_progress.visibility = View.VISIBLE
            enter_button.enabled(false)
            val birthDate = date_input.text.toString()
            val city = city_input.selectedItemPosition + 1
            val name = name_input.text.toString()
            Log.d("UserAuth", "$birthDate $city $name")
            UserRepository().register(intent.getStringExtra(EXTRA_FIREBASE_TOKEN)!!, birthDate, city, name,
            object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    enter_button.enabled(true)
                    enter_progress.visibility = View.GONE
                    if (response.isSuccessful) onSuccessfulResponse(response.body()!!)
                    else Log.e("UserAuth", "Registration failure: code ${response.code()}")
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("UserAuth", "Registration failure", t)
                    enter_button.enabled(true)
                    enter_progress.visibility = View.GONE
                }

            })
        }
        date_input.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val d = dayOfMonth.toString().padStart(2, '0')
                val m = (monthOfYear + 1).toString().padStart(2, '0')
                date_input.setText("$d.$m.$year")
                currentYear = year
                currentMonth = monthOfYear
                currentDay = dayOfMonth
            }
            DatePickerDialog(this, dateSetListener, currentYear, currentMonth, currentDay).show()
        }
    }

    fun onSuccessfulResponse(response: LoginResponse) {
        getSharedPreferences(USER_ACCESS_INFO, Context.MODE_PRIVATE).edit().apply {
            putString(USER_ACCESS_TOKEN, response.accessToken)
            putString(USER_REFRESH_TOKEN, response.refreshToken)
            putInt(USER_CURRENT_ID, response.user.id.toInt())
            apply()
        }
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    fun updateButtonState() {
        if (nameIsValid && date_input.text.isNotEmpty() && materialCheckBox.isChecked) {
            enter_button.enabled(true)
        }
        else enter_button.enabled(false)
    }

    private fun Button.enabled(enabled: Boolean) {
        isEnabled = enabled
        alpha = if (enabled) 1F else 0.5F
    }
}