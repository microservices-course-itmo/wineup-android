package com.itmo.wineup.features.profile.presentation

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.itmo.wineup.MainActivity
import com.itmo.wineup.R
import com.itmo.wineup.network.retrofit.user.UserService
import kotlinx.android.synthetic.main.fragment_confirm_code.*
import java.util.concurrent.TimeUnit

class ConfirmCodeFragment : Fragment() {


    lateinit var timer: CountDownTimer

    private var codeSent = false
    private var lengthOk = false
    private var phone: String? = null

    private lateinit var verificationId: String
    private lateinit var authOptions: PhoneAuthOptions

    companion object {
        fun newInstance() =
            ConfirmCodeFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_confirm_code, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phone = arguments?.getString("phone")
        phone?.let {
            authOptions = PhoneAuthOptions.newBuilder(Firebase.auth)
                .setPhoneNumber(it) // Phone number to verify
                .setTimeout(9, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(activity as MainActivity) // Activity (for callback binding)
                .setCallbacks(returnCallBack()) // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(authOptions)
            initTimer()
            startTimer()
            setListeners()
        }

    }

    private fun returnCallBack() = object : PhoneAuthProvider
    .OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Firebase.auth.currentUser?.updatePhoneNumber(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.e("phone",  e.toString())

        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            verificationId = p0
            Log.d("Auth", "Code sent, id: $p0")
            codeSent = true
            confirmation_button.enabled(codeSent && lengthOk)
            super.onCodeSent(p0, p1)
        }
    }

    fun confirmChange() {
        Firebase.auth.currentUser
            ?.updatePhoneNumber(PhoneAuthProvider.getCredential(verificationId, code_edit_text.text.toString()))
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val stubUser = JsonObject().apply {
                        add("birthday", JsonNull.INSTANCE)
                        add("cityId", JsonNull.INSTANCE)
                        add("name", JsonNull.INSTANCE)
                        add("phoneNumber", JsonNull.INSTANCE)
                    }
                    UserService.api().patchUser(stubUser)
                    Toast.makeText(context, "Изменения успешно сохранены!", Toast.LENGTH_SHORT).show()
                    timer.cancel()
                    (activity as MainActivity).backFromConfirmCodeFragment()
                }
                else {
                    wrong_code.visibility = View.VISIBLE
                }
            }
//            ?.addOnFailureListener {
//                timer.cancel()
//                Toast.makeText(context, "Не удалось обновить номер!", Toast.LENGTH_SHORT).show()
//                (activity as MainActivity).backFromConfirmCodeFragment()
//            }

    }



    private fun setListeners() {
        back.setOnClickListener {
            timer.cancel()
            (activity as MainActivity).backFromConfirmCodeFragment()
        }
        confirmation_button.setOnClickListener { confirmChange() }
        button_resend_code.setOnClickListener { resendCode() }

        code_edit_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                wrong_code.visibility = View.INVISIBLE
                lengthOk = s?.length == 6
                confirmation_button.enabled(codeSent && lengthOk)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun resendCode() {
        codeSent = false
        code_edit_text.text.clear()
        PhoneAuthProvider.verifyPhoneNumber(authOptions)
        wrong_code.visibility = View.INVISIBLE
        button_resend_code.isVisible = false
        startTimer()
    }

    private fun startTimer() {
        timer_counter.visibility = View.VISIBLE
        timer.start()
    }

    private fun initTimer() {
        timer = object : CountDownTimer(10000, 1000) { //60000
            override fun onFinish() {
                button_resend_code.isVisible = true
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