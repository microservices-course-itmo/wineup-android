package com.itmo.wineup.features.auth.presentation


import android.content.Intent
import android.graphics.Color.alpha
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.internal.BackgroundDetector
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_phone_number_access.*

class PhoneNumberAccessActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number_access)

        phoneNumberInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                authButton.enabled(s?.length == 11)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        authButton.setOnClickListener{
            startActivity(Intent(this, CodeInputActivity::class.java))
    }


        withoutAuthButton.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun Button.enabled(enabled: Boolean) {
        isEnabled = enabled
        alpha = if (enabled) 1F else 0.5F
    }

}