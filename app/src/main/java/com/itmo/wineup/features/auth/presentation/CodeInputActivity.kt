package com.itmo.wineup.features.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import kotlinx.android.synthetic.main.activity_code_input.*
import java.util.concurrent.TimeUnit

class CodeInputActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PHONE_NUMBER = "PhoneNumber"
    }

    lateinit var timer: CountDownTimer
    private lateinit var viewModel: CodeInputViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var authOptions: PhoneAuthOptions
    private var codeSent = false
    private var lengthOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_input)
        viewModel = ViewModelProvider(this).get(CodeInputViewModel::class.java)
        prepareAuth()
        PhoneAuthProvider.verifyPhoneNumber(authOptions)
        initTimer()
        startTimer()
        setListeners()
    }

    private fun prepareAuth() {
        auth = Firebase.auth
        auth.useAppLanguage()
        val phoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER)!!
        Log.d("Phone", phoneNumber)
        authOptions = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(9, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Log.d("Auth", "Verification completed")
                    signIn(p0)
                }
                override fun onVerificationFailed(p0: FirebaseException) {
                    Log.d("Auth", "${p0.message}")
                    Toast.makeText(applicationContext, "Verification failed, see logs", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    verificationId = p0
                    Log.d("Auth", "Code sent, id: $p0")
                    codeSent = true
                    enter_button.enabled(codeSent && lengthOk)
                    super.onCodeSent(p0, p1)
                }
            }).build()
    }

    private fun setListeners() {
        back_button.setOnClickListener { onBackPressed() }
        enter_button.setOnClickListener { validateCode() }
        button_resend_code.setOnClickListener { resendCode() }

        code_edit_text.addTextChangedListener(object : TextWatcher {
            
            override fun afterTextChanged(s: Editable?) {
                wrong_code.visibility = View.INVISIBLE
                lengthOk = s?.length == 6
                enter_button.enabled(codeSent && lengthOk)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun signIn(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    val user = it.result?.user
                    user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                        val token = tokenTask.result?.token
                        Log.d("Auth", "Got token response: $token")
                        startActivity(Intent(this, MainActivity::class.java))
                    } ?: Log.d("Auth", "Unexpected error: user is null")
                }
                it.exception is FirebaseAuthInvalidCredentialsException -> wrong_code.visibility = View.VISIBLE
                else -> {
                    Log.e("Auth", "Auth error", it.exception)
                    Toast.makeText(applicationContext, "Unexpected error while authenticating", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateCode() {
        val credential = PhoneAuthProvider.getCredential(verificationId, code_edit_text.text.toString())
        signIn(credential)
    }

    private fun resendCode() {
        codeSent = false
        code_edit_text.text.clear()
        PhoneAuthProvider.verifyPhoneNumber(authOptions)
        wrong_code.visibility = View.INVISIBLE
        button_resend_code.enabled(false)
        startTimer()
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