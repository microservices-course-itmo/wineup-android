package com.itmo.wineup.features.auth.presentation

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itmo.wineup.features.auth.USER_ACCESS_TOKEN
import com.itmo.wineup.features.auth.USER_CURRENT_ID
import com.itmo.wineup.features.auth.USER_REFRESH_TOKEN
import com.itmo.wineup.features.auth.data.UserRepository
import com.itmo.wineup.network.retrofit.user.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgeAccessViewModel : ViewModel() {

    val accessStateLiveData = MutableLiveData<AccessState>(AccessState.WAITING)
    private val repo = UserRepository()
    lateinit var preferences : SharedPreferences

    private fun updateState(state : AccessState) {
        accessStateLiveData.value = state
    }

    fun skipLogin() {
        updateState(AccessState.DENIED)
    }

    fun login() {
        val accessToken = preferences.getString(USER_ACCESS_TOKEN, "")
        if (accessToken.isNullOrEmpty()) {
            updateState(AccessState.DENIED)
            return
        }
        repo.validateAccessToken(accessToken, object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("UserAuth", "Login failure", t)
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful)
                    accessStateLiveData.value = AccessState.GRANTED
                else refresh()
            }
        })
    }

    fun refresh() {
        Log.d("UserAuth", "Refreshing login")
        val refreshToken = preferences.getString(USER_REFRESH_TOKEN, "")
        if (refreshToken.isNullOrEmpty()) {
            updateState(AccessState.DENIED)
            return
        }
        repo.refresh(refreshToken, object : Callback<LoginResponse> {
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
                else updateState(AccessState.DENIED)
            }
        })
    }

    enum class AccessState {
        GRANTED, WAITING, DENIED
    }
}