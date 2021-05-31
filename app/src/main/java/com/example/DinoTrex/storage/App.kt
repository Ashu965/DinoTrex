package com.example.DinoTrex.storage

import android.app.Application

val prefs : Prefs by lazy {
    App.prefs!!
}

class App : Application() {

    companion object{
        var prefs : Prefs? = null
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}