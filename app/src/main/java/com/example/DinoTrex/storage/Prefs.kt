package com.example.DinoTrex.storage

import android.content.Context
import android.content.SharedPreferences

class Prefs(context : Context) {
    private val PREF_FILENAME = "com.RunningGame.prefs"
    private val SCORE_VALUE = "score_value"
    private val prefs : SharedPreferences = context.getSharedPreferences(PREF_FILENAME,0)

    var score : Int
        get() {
         return prefs.getInt(SCORE_VALUE,0)
        }
        set(value) {
            prefs.edit().putInt(SCORE_VALUE,value).apply()
        }
}