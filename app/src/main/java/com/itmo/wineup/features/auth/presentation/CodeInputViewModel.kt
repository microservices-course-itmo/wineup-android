package com.itmo.wineup.features.auth.presentation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.itmo.wineup.features.auth.USER_ACCESS_TOKEN
import com.itmo.wineup.features.auth.USER_CURRENT_ID
import com.itmo.wineup.features.auth.USER_REFRESH_TOKEN
import com.itmo.wineup.network.retrofit.user.LoginResponse
import com.itmo.wineup.features.auth.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CodeInputViewModel : ViewModel() {

    val accessStateLiveDate = MutableLiveData(AccessState.WAITING)
    lateinit var firebaseToken : String
    private val repo = UserRepository()
    lateinit var preferences: SharedPreferences

    private fun updateState(state : AccessState) {
        accessStateLiveDate.value = state
    }

    fun login(user: FirebaseUser) {
        user.getIdToken(true).addOnCompleteListener {
            val token = it.result?.token!!
            firebaseToken = token
            Log.d("Auth", "Got token response: $token")
            performLoginWithFirebaseToken(token)
        }
    }

    fun performLoginWithFirebaseToken(token: String) {
        repo.loginWithFirebaseToken(token, object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("UserAuth", "Login failure", t)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()!!
                    preferences.edit().apply {
                        putString(USER_ACCESS_TOKEN, body.accessToken)
                        putString(USER_REFRESH_TOKEN, body.refreshToken)
                        putInt(USER_CURRENT_ID, body.user.id.toInt())
                        apply()
                    }
                    updateState(AccessState.GRANTED)
                }
                else {
                    when (response.code()) {
                        401 -> updateState(AccessState.UNAUTHORIZED)
                        418 -> updateState(AccessState.INVALID_TOKEN)
                        else -> updateState(AccessState.ERROR)
                    }
                }
            }
        })
    }

    enum class AccessState {
        WAITING, GRANTED, UNAUTHORIZED, INVALID_TOKEN, ERROR
    }

}