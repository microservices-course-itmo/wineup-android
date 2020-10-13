package com.itmo.wineup.features.auth


import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_phone_number_access.*

class PhoneNumberAccessActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number_access)

        var phoneNumber: String

        authButton.setOnClickListener(View.OnClickListener {
            phoneNumber = phoneNumberInput.toString()
            if(phoneNumber.length in 11 downTo 1){
                for (char in phoneNumber){
                    if (char in phoneNumber){
                        if (char.isDigit()) {
                            authButton.isEnabled = true
//                            if (phoneNumber.startsWith("89") )
                        }
                    }
                }
            }
        })

/*        val authButton: Button = findViewById(R.id.authButton)
        authButton.setOnClickListener {
            val text = "Wrong phone number!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.setGravity(Gravity.CENTER, 0, 0)
        }*/

        authButton.setOnClickListener{
            val intent = Intent (this, CodeInputActivity::class.java)
            startActivity(intent)
        }

        withoutAuthButton.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }


        fun Button.enabled(enabled: Boolean) {
            isEnabled = enabled
            setTextColor(ContextCompat.getColor(context, if (enabled) R.color.white else R.color.light_red))
            setBackgroundColor(ContextCompat.getColor(context, if (enabled) R.color.light_red else R.color.beige))
        }
    }

}