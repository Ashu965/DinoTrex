package com.example.DinoTrex.model

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.DinoTrex.other.AssetPath
import com.example.DinoTrex.other.Constants.Companion.DOUBT_FACTOR
import com.example.DinoTrex.other.Constants.Companion.dinoPos
import com.example.DinoTrex.other.Constants.Companion.minDistanceBetween
import com.example.DinoTrex.other.Constants.Companion.roadLength
import com.example.DinoTrex.other.Constants.Companion.roadPosition
import com.example.DinoTrex.other.Constants.Companion.xVelocity
import com.example.DinoTrex.util.Converter.convertDpToPixels

val doubt_factor = convertDpToPixels(DOUBT_FACTOR.value)

class CactusState(val  cactusList : ArrayList<Cactus> = ArrayList()) {

    fun init() {
        var startx = roadLength

        for (i in 0..2) {
            val cactus = Cactus(startx)
            cactusList.add(cactus)
            startx += (minDistanceBetween + (0..minDistanceBetween.value.toInt()).random().dp)
        }
    }

    fun move(score: MutableState<Int>,birdState: BirdState) {
        for (cactus in cactusList) {
            if(!birdState.isMoving || cactus.xpos< roadLength)
            cactus.xpos -= xVelocity
            if (!cactus.croosDino && cactus.xpos < dinoPos) {
                score.value++
                cactus.croosDino = true
            }
            if (cactus.xpos < (-50).dp) {
                reallocate(cactus)
                cactus.croosDino = false
            }
        }
    }

    private fun reallocate(cactus: Cactus) {
        if (cactusList.last().xpos < roadLength)
            cactus.xpos =
                roadLength + (minDistanceBetween + (0..minDistanceBetween.value.toInt()).random().dp)
        else
            cactus.xpos =
                cactusList.last().xpos + (minDistanceBetween + (0..minDistanceBetween.value.toInt()).random().dp)
    }

    fun destroy() {
        while (cactusList.isNotEmpty())
            cactusList.removeAt(0)
    }

    fun draw(drawScope: DrawScope,birdState: BirdState) {
            for (cactus in cactusList) {
                if(!birdState.isMoving || (cactus.xpos< roadLength)) {
                    drawScope.apply {
                        withTransform({
                            val cactusPos = convertDpToPixels(cactus.xpos.value)
                            translate(
                                left = cactusPos,
                                top = convertDpToPixels(roadPosition.value) - AssetPath().CactusPath()
                                    .getBounds().height
                            )
                        }) {
                            drawPath(
                                path = AssetPath().CactusPath(),
                                style = Fill,
                                color = Color.Red
                            )
                        }
                        //val rect = cactus.getRect()
                       // drawRect(color = Color.Blue, rect.topLeft, rect.size, style = Stroke(3f))
                       // drawRect(color = Color.Blue, rect.deflate(doubt_factor).topLeft, rect.deflate(doubt_factor).size, style = Stroke(3f))
                    }
                }
            }
    }

    class Cactus(var xpos: Dp, var croosDino: Boolean = false) {
        fun getRect(): Rect {
            val xposInFloat =convertDpToPixels(xpos.value)
            return Rect(
                left = xposInFloat,
                top = convertDpToPixels(roadPosition.value) - AssetPath().CactusPath().getBounds().height,
                right = xposInFloat + AssetPath().CactusPath().getBounds().width,
                bottom = convertDpToPixels(roadPosition.value)
            )
        }
    }
}