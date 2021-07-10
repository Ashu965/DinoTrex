package com.example.DinoTrex

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.DinoTrex.other.Constants.Companion.cloudHeight
import com.example.DinoTrex.other.Constants.Companion.minDistanceBetween
import com.example.DinoTrex.other.Constants.Companion.roadLength
import com.example.DinoTrex.other.Constants.Companion.xVelocity
import com.example.DinoTrex.other.AssetPath
import com.example.DinoTrex.util.Converter

//More than one cloud appears at a time.
// Hence, this state controls the multiple cloud states within.
class CloudState(val cloudList : ArrayList<Cloud> = ArrayList()) {
    fun init(){
        var startx = roadLength
        for( i in 0..1){
           val cloud = Cloud(startx)
            cloudList.add(cloud)
           startx += minDistanceBetween+ (0..minDistanceBetween.value.toInt()).random().dp
        }
    }

    ///this function decrease position of each cloud on every time frame
    // so that they appear to be moving.
    fun move(){
        for(cloud in cloudList){
            cloud.xpos -= xVelocity
            if(cloud.xpos<-10.dp)
                cloud.xpos = roadLength
        }
    }
    fun draw(drawScope: DrawScope){
        for(cloud in cloudList){
            val cloudXpos = Converter.convertDpToPixels(cloud.xpos.value)
            drawScope.apply {
                withTransform({
                    translate(
                        left = cloudXpos,
                        top = Converter.convertDpToPixels(cloudHeight.value)
                    )
                }) {
                    drawPath(
                        path = AssetPath().CloudPath(),
                        color = Color.Red
                    )
                }
            }
        }
    }
}
class Cloud(var xpos : Dp){

}