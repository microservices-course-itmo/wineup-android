package com.itmo.wineup.features.auth.presentation


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_phone_number_access.*
import kotlin.math.min

class PhoneNumberAccessActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number_access)

        phoneNumberInput.addTextChangedListener(object : TextWatcher {

            private var editFlag = false

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!editFlag) {
                    editFlag = true
                    val string = s.toString()
                    var phone = string.replaceFirst("7", "").filter { it.isDigit() }
                    val phoneLength = phone.length
                    var startInPhone = when (start) {
                            in (4..7) -> start - 3
                            in (7..12) -> start - 5
                            in (12..15) -> start - 6
                            in (15..18) -> start - 7
                            else -> 18
                        } - before
                    if (startInPhone > phoneLength) startInPhone = phoneLength
                    val cursorFix = when (startInPhone) {
                            in (0..3) -> 4
                            in (4..6) -> 6
                            in (6..8) -> 7
                            in (8..11) -> 8
                            else -> 0
                        }
                    val cursor = startInPhone + cursorFix
                    if (phoneLength < 10) {
                        authButton.enabled(false)
                        phone += "X".repeat(10 - phoneLength)
                    }
                    else authButton.enabled(true)
                    val res = "+7 (${phone.substring(0, 3)})-${phone.substring(3, 6)}-${phone.substring(6, 8)}-${phone.substring(8, 10)}"
                    phoneNumberInput.setText(res)
                    phoneNumberInput.setSelection(min(18, cursor))
                }
                else editFlag = false
            }
        })
        authButton.setOnClickListener{
            val phone = "+" + phoneNumberInput.text.filter { it.isDigit() }
            val codeIntent = Intent(this, CodeInputActivity::class.java)
            codeIntent.putExtra(CodeInputActivity.EXTRA_PHONE_NUMBER, phone)
            startActivity(codeIntent)
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