package com.itmo.wineup.features.profile.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itmo.wineup.features.auth.data.UserRepository
import com.itmo.wineup.network.retrofit.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {

    val phoneLiveData = MutableLiveData<String>("")

    fun getUserById(id : Int) {
        UserRepository().getUserById(id, object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {}

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    phoneLiveData.value = response.body()!!.phoneNumber
                }
            }
        })
    }

}