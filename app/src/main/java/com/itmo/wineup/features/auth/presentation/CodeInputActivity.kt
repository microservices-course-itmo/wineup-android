package com.itmo.wineup.features.auth.presentation

import android.content.Context
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import com.itmo.wineup.features.auth.USER_ACCESS_INFO
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
        viewModel.preferences = getSharedPreferences(USER_ACCESS_INFO, Context.MODE_PRIVATE)
        prepareAuth()
        PhoneAuthProvider.verifyPhoneNumber(authOptions)
        initTimer()
        startTimer()
        setListeners()
        viewModel.accessStateLiveDate.observe(this, {
            hideProgress()
            when (it) {
                CodeInputViewModel.AccessState.GRANTED -> startActivity(Intent(applicationContext, MainActivity::class.java))
                CodeInputViewModel.AccessState.UNAUTHORIZED -> {
                    val registrationIntent = Intent(applicationContext, RegistrationActivity::class.java)
                    registrationIntent.putExtra(RegistrationActivity.EXTRA_FIREBASE_TOKEN, viewModel.firebaseToken)
                    startActivity(registrationIntent)
                }
                CodeInputViewModel.AccessState.INVALID_TOKEN -> Log.e("UserAuth", "Login unsuccessful. Incorrect firebase token.")
                CodeInputViewModel.AccessState.ERROR -> Log.e("UserAuth", "Unknown error")
                else -> {}
            }
        })
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
        auth.signInWithCredential(credential).addOnCompleteListener {task ->
            when {
                task.isSuccessful -> {
                    task.result?.user?.let { viewModel.login(it) }
                        ?: Log.d("Auth", "Unexpected error: user is null")
                }
                task.exception is FirebaseAuthInvalidCredentialsException -> {
                    wrong_code.visibility = View.VISIBLE
                    hideProgress()
                }
                else -> {
                    Log.e("Auth", "Auth error", task.exception)
                    Toast.makeText(applicationContext, "Unexpected error while authenticating", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateCode() {
        enter_button.enabled(false)
        login_progress.visibility = View.VISIBLE
        val credential = PhoneAuthProvider.getCredential(verificationId, code_edit_text.text.toString())
        signIn(credential)
    }

    private fun hideProgress() {
        login_progress.visibility = View.GONE
        enter_button.enabled(true)
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