package com.itmo.wineup.network.retrofit.user

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserResponse
)