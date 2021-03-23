package com.itmo.wineup.features.profile.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.itmo.wineup.features.auth.data.UserRepository
import com.itmo.wineup.network.retrofit.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileDataEditViewModel : ViewModel() {
    
    val userChangeSuccess = MutableLiveData<String>()

    fun patchUser(updatedUser: JsonObject) {
        UserRepository().patchUser(updatedUser, object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.i("tag", "msg")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                userChangeSuccess.value = "success"
            }
        })
    }
}