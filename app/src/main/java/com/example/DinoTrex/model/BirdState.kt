package com.example.DinoTrex.model

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import com.example.DinoTrex.other.AssetPath
import com.example.DinoTrex.other.Constants
import com.example.DinoTrex.other.Constants.Companion.birdHeight
import com.example.DinoTrex.util.Converter


class BirdState(var xpos :Dp = Constants.roadLength, var crossDino :Boolean = false, var targetScore : Int = 3) {
    var isMoving = false
    fun move(score : MutableState<Int>){
            xpos -= Constants.xVelocity
        if (!crossDino && xpos < Constants.dinoPos) {
            score.value++
            crossDino = true
        }
        }

    fun destroy(){
       xpos = Constants.roadLength
    }
    fun increaseTargetedScore(){
        targetScore = (targetScore+3..targetScore+6).random()
    }

    fun draw(drawScope: DrawScope){
            val birdXpos = Converter.convertDpToPixels(xpos.value)
            drawScope.apply {
                withTransform({
                    translate(
                        left = birdXpos,
                        top = Converter.convertDpToPixels(birdHeight.value) - AssetPath().BirdPath().getBounds().height
                    )
                }) {
                    drawPath(
                        path = AssetPath().BirdPath(),
                        color = Color.Red
                    )
                }
                val rect = getRect()
               // drawRect(color = Color.Blue,rect.topLeft,rect.size,style = Stroke(3f))
            }
        }
    fun getRect() : Rect {
        val resource = AssetPath().BirdPath()
        return  Rect(
            left = Converter.convertDpToPixels(xpos.value),
            top =  Converter.convertDpToPixels(birdHeight.value) - resource.getBounds().height,
            right = Converter.convertDpToPixels(xpos.value) + resource.getBounds().width,
            bottom = Converter.convertDpToPixels(birdHeight.value)
        )
    }
    }