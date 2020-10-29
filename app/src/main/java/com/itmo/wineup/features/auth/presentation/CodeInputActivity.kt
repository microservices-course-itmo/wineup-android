package com.itmo.wineup.features.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_code_input.*
import java.util.concurrent.TimeUnit

class CodeInputActivity : AppCompatActivity() {

    lateinit var timer: CountDownTimer
    private lateinit var viewModel: CodeInputViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_input)
        viewModel = ViewModelProvider(this).get(CodeInputViewModel::class.java)
        initTimer()
        startTimer()
        setListeners()
    }

    private fun setListeners() {
        back_button.setOnClickListener { onBackPressed() }
        enter_button.setOnClickListener { validateCode() }
        button_resend_code.setOnClickListener { resendCode() }

        code_edit_text.addTextChangedListener(object : TextWatcher {
            
            override fun afterTextChanged(s: Editable?) {
                wrong_code.visibility = View.INVISIBLE
                enter_button.enabled(s?.length == 6)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun validateCode() {
        //now only 000000 is right code
        if (viewModel.validateCode(code_edit_text.text.toString())) {
            //todo: navigate to next screen. You need to input your activity name here.
            startActivity(Intent(this, RegistrationActivity::class.java))
        } else {
            wrong_code.visibility = View.VISIBLE
        }
    }

    private fun resendCode() {
        code_edit_text.text.clear()
        wrong_code.visibility = View.INVISIBLE
        button_resend_code.enabled(false)
        startTimer()
        viewModel.getNewCode()
    }

    private fun startTimer() {
        timer_counter.visibility = View.VISIBLE
        timer.start()
    }

    private fun initTimer() {
        timer = object : CountDownTimer(10000, 1000) { //60000
            override fun onFinish() {
                button_resend_code.enabled(true)
                timer_counter.visibility = View.INVISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
                timer_counter.text = getString(R.string.timer_test).format(
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toString()
                )
            }
        }
    }

    private fun Button.enabled(enabled: Boolean) {
        isEnabled = enabled
        alpha = if (enabled) 1F else 0.5F
    }


}