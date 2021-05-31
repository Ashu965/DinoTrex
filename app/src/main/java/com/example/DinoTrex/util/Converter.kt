package com.example.DinoTrex.util

import com.example.DinoTrex.deviceDensity

object Converter {

   fun convertPixelsToDp( pixels: Int) =
        (pixels / deviceDensity).toFloat()

    fun convertDpToPixels( dp: Float) =
        (dp * deviceDensity)
}
