package com.itmo.wineup.features.profile.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itmo.wineup.features.auth.data.UserRepository
import com.itmo.wineup.network.retrofit.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {

    val phoneLiveData = MutableLiveData("")
    val nameLiveData = MutableLiveData("")
    val cityLiveData = MutableLiveData(1)
    val nonAuthUser = MutableLiveData(false)

    fun currentUser() {
        UserRepository().currentUser(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.i("tag", "msg")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    phoneLiveData.value = response.body()!!.phoneNumber
                    nameLiveData.value = response.body()!!.name
                    cityLiveData.value = response.body()!!.cityId
                } else if (response.code() == 403){
                    nonAuthUser.value = true
                }
            }
        })
    }

}