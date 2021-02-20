package com.itmo.wineup

object TokenMaster {

    lateinit var accessToken: String
    private set

    lateinit var refreshToken: String
    private set

    fun init(access: String, refresh: String) {
        accessToken = access
        refreshToken = refresh
    }
}