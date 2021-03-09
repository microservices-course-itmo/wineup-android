package com.itmo.wineup

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var instance: App

        fun getContext() = instance as Context
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}