package com.itmo.wineup.features.profile.model

import java.io.Serializable

data class Profile(
    val name: String,
    val phone: String,
    val cityId: Int
) : Serializable