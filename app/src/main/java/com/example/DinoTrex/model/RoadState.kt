package com.example.DinoTrex.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.DinoTrex.other.Constants.Companion.discreateRoadThickness
import com.example.DinoTrex.other.Constants.Companion.distanceBetweenRoad
import com.example.DinoTrex.other.Constants.Companion.roadLength
import com.example.DinoTrex.other.Constants.Companion.roadPosition
import com.example.DinoTrex.other.Constants.Companion.roadThickness
import com.example.DinoTrex.other.Constants.Companion.xVelocity
import com.example.DinoTrex.util.Converter.convertDpToPixels

data class RoadState(var xpos : Dp = 0.dp) {

   fun move() {
       xpos -= xVelocity
       if (xpos < -roadLength)
           xpos = 0.dp
   }

    fun drawRoad(drawScope: DrawScope) {
        drawScope.apply{
            withTransform({
                translate(
                    left = convertDpToPixels(xpos.value),
                    top = 0f
                )
            }) {
                drawLine(
                    color = Color.Red,
                    start = Offset(0f, convertDpToPixels(roadPosition.value)),
                    end = Offset(
                        2 * convertDpToPixels(roadLength.value),
                        convertDpToPixels(roadPosition.value)
                    ),
                    strokeWidth = convertDpToPixels(roadThickness.value),
                )
                drawLine(
                    color = Color.Red,
                    start = Offset(x = 0f, y = convertDpToPixels(roadPosition.value)
                            + convertDpToPixels(distanceBetweenRoad.value)),
                    end = Offset(
                        x = 2 * convertDpToPixels(roadLength.value), y = convertDpToPixels(
                            roadPosition.value
                        ) + 30
                    ),
                    strokeWidth = convertDpToPixels(discreateRoadThickness.value),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 25f)
                )
            }
        }
    }
}