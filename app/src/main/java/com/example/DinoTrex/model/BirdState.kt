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

//This state defines various characterstic of bird like After which score it will get draw on the
// ui and position of the bird.

class BirdState(var xpos :Dp = Constants.roadLength, var crossDino :Boolean = false, var targetScore : Int = 3) {
    var isMoving = false

    //changes the position of bird by reducing xpos value.
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

    //set target score at which bird will appear in the game.
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
            }
        }


    fun getRect() : Rect {
        val resource = AssetPath().BirdPath() //Function to get rectangle around the bird.
        return  Rect(
            left = Converter.convertDpToPixels(xpos.value),
            top =  Converter.convertDpToPixels(birdHeight.value) - resource.getBounds().height,
            right = Converter.convertDpToPixels(xpos.value) + resource.getBounds().width,
            bottom = Converter.convertDpToPixels(birdHeight.value)
        )
    }
    }