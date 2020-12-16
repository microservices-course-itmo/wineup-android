package com.itmo.wineup.network.retrofit.user

data class UserResponse(
    val id: String,
    val phoneNumber: String,
    val name: String,
    val role: String
)