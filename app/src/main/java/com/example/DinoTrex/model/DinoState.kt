package com.example.DinoTrex.model

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.DinoTrex.other.AssetPath
import com.example.DinoTrex.other.Constants.Companion.dinoGravity
import com.example.DinoTrex.other.Constants.Companion.dinoPos
import com.example.DinoTrex.other.Constants.Companion.dinoUpVelocity
import com.example.DinoTrex.other.Constants.Companion.roadPosition
import com.example.DinoTrex.util.Converter.convertDpToPixels

//This controls the state of the dino in its vertical movement (ypos).
data class DinoState(var ypos: Dp = roadPosition, var velocity : Dp = 0.dp) {
    var gravity = 0.dp

    fun init(){
        ypos += velocity
        velocity += gravity
        if(ypos> roadPosition){
            ypos = roadPosition
            velocity=0.dp
            gravity=0.dp
        }
    }

    //To make the dino jump ,it has given some upvelocity which changes its ypos , along with that
    //some gravity also given to decrease the upvelocity with time so that it can come back to
    //its original position.
    fun jump(){
        Log.i("ashu","road = $roadPosition")
        Log.i("ashu", "ypos - $ypos")
        if(ypos == roadPosition){
            velocity= dinoUpVelocity
            gravity = dinoGravity
        }
    }

    fun draw(drawScope: DrawScope, tick : Float,gameEnd : Boolean){
        val resource : Path
        if (tick<= 0.5f)
            resource = AssetPath().DinoPath()
        else
            resource = AssetPath().DinoPath2()

        val rotateValue = when(gameEnd){
              false -> 0f
              else -> 180f
        }

        drawScope.apply {
            withTransform({
                translate(
                    left = convertDpToPixels(dinoPos.value),
                    top = convertDpToPixels(ypos.value) - resource.getBounds().height
                )
            }) {
                rotate(
                    degrees = rotateValue,
                    pivot = Offset(resource.getBounds().width / 2, resource.getBounds().height / 2)
                ) {
                    drawPath(
                        path = resource,
                        style = Fill,
                        color = if (!gameEnd) Color.Red
                        else Color.White
                    )
                }
            }
            //val rect = getRect()
           // drawRect(color = Color.Blue,rect.topLeft,rect.size,style = Stroke(3f))
           // drawRect(color = Color.Blue, rect.deflate(doubt_factor).topLeft, rect.deflate(doubt_factor).size, style = Stroke(3f))
        }
    }


    //Function to get rectangle around the dino.
    fun getRect() : Rect{
        val resource = AssetPath().DinoPath()
        return  Rect(
            left = convertDpToPixels(dinoPos.value),
            top =  convertDpToPixels(ypos.value) - resource.getBounds().height,
            right = convertDpToPixels(dinoPos.value) + resource.getBounds().width,
            bottom = convertDpToPixels(ypos.value)
        )
    }
}