package com.itmo.wineup.features.auth.data

import com.google.gson.JsonObject
import com.itmo.wineup.network.retrofit.user.LoginResponse
import com.itmo.wineup.network.retrofit.user.UserResponse
import com.itmo.wineup.network.retrofit.user.UserService
import retrofit2.Callback

class UserRepository {

    fun loginWithFirebaseToken(token: String, callback: Callback<LoginResponse>) {
        val tokenObject = JsonObject().apply {
            addProperty("fireBaseToken", token)
        }
        UserService.api().login(tokenObject).enqueue(callback)
    }

    fun register(firebaseToken: String, birthDate: String, cityId: Int, name: String, callback: Callback<LoginResponse>) {
        val requestObject = JsonObject().apply {
            addProperty("birthday", birthDate)
            addProperty("cityId", cityId)
            addProperty("fireBaseToken", firebaseToken)
            addProperty("name", name)
        }
        UserService.api().register(requestObject).enqueue(callback)
    }

    fun refresh(refreshToken: String, callback: Callback<LoginResponse>) =
        UserService.api().refresh(refreshToken).enqueue(callback)

    fun validateAccessToken(token: String, callback: Callback<Unit>) =
        UserService.api().validate(token).enqueue(callback)

    fun getUserById(id: Int, callback: Callback<UserResponse>) =
        UserService.api().user(id).enqueue(callback)

    fun currentUser(callback: Callback<UserResponse>) =
        UserService.api().currentUser().enqueue(callback)

    fun patchUser(updatedUser: JsonObject, callback: Callback<UserResponse>) =
        UserService.api().patchUser(updatedUser).enqueue(callback)


}