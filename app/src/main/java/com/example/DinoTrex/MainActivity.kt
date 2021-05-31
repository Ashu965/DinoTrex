package com.example.DinoTrex
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kotlin.properties.Delegates

var deviceWidthInPixel by Delegates.notNull<Float>()
var deviceDensity by Delegates.notNull<Float>()

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var deviceMatrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics( deviceMatrics)

        deviceWidthInPixel = deviceMatrics.widthPixels.toFloat()
        deviceDensity = deviceMatrics.density
        setContent {
                // A surface container using the 'background' color from the theme
               startGameLoop(this)
            }
        }
    }