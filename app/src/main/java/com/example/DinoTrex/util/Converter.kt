package com.example.DinoTrex.util

import com.example.DinoTrex.deviceDensity

//Converters so that objects appear to be of same size in all the devices.
object Converter {

   fun convertPixelsToDp( pixels: Int) =
        (pixels / deviceDensity).toFloat()

    fun convertDpToPixels( dp: Float) =
        (dp * deviceDensity)
}
