package com.example.DinoTrex

import android.content.Context
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.DinoTrex.media.SoundEffect
import com.example.DinoTrex.other.Constants.Companion.roadLength
import com.example.DinoTrex.model.*
import com.example.DinoTrex.storage.prefs

/*Main GameLoop. It runs continuously during gameplay. Each turn of the loop,
it processes user input without blocking, updates the game state, and renders the game*/
@Composable
fun startGameLoop(context: Context){

    val soundEffect = remember { SoundEffect(context) }
    val roadState = remember{ RoadState() }
    var cactusState = remember{ CactusState() }
    val dinoState = remember{ DinoState() }
    var scoreState = remember{ mutableStateOf(0) }
    var highestScoreState = remember{ mutableStateOf(prefs.score) }
    var cloudState = remember { CloudState() }
    val birdState = remember{ BirdState() }

    val state = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 500)
        )
    )

    //Display bird after a particular score value.
    if(birdState.targetScore == scoreState.value)
        birdState.isMoving = true

    if(cactusState.cactusList.isEmpty())
        cactusState.init()
    if(cloudState.cloudList.isEmpty())
        cloudState.init()

    var gameEnd by remember{ mutableStateOf(false) }
    val tick = state.value

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .clickable {
                if (!gameEnd) {
                    dinoState.jump()
                    soundEffect.playJumpSound()
                } else {
                    gameEnd = false
                    cactusState.destroy()
                    birdState.destroy()
                    if (highestScoreState.value < scoreState.value) {
                        highestScoreState.value = scoreState.value
                    }
                    birdState.isMoving=false
                    birdState.targetScore=3
                    scoreState.value = 0
                }
            }) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            dinoState.draw(this, tick,gameEnd)
            cactusState.draw(this,birdState)
            cloudState.draw(this)
            roadState.drawRoad(this)
            if(birdState.isMoving)
            birdState.draw(this)
        }
        Score(scoreState.value, highestScoreState.value)

        if(birdState.xpos<0.dp) {
            birdState.isMoving = false
            birdState.xpos= roadLength
            birdState.increaseTargetedScore()
        }

        val dinoRect = dinoState.getRect()
        val birdRect = birdState.getRect()
        for (cactus in cactusState.cactusList) {
            if (checkCollision(dinoRect.deflate(doubt_factor), cactus.getRect().deflate(doubt_factor)) || checkCollision(dinoRect.deflate(
                    doubt_factor),birdRect.deflate(doubt_factor))) {
                if(!gameEnd){
                    soundEffect.playGameEndSound()
                    if(scoreState.value > prefs.score)
                    prefs.score = scoreState.value}
                gameEnd = true

            }
        }
        GameOver(modifier = Modifier
            .wrapContentSize(Alignment.TopCenter)
            .padding(top = 150.dp), gameEnd)
    }
    if(!gameEnd){
        cactusState.move(scoreState,birdState)
        dinoState.init()
        cloudState.move()
        roadState.move()
        if(birdState.isMoving)
            birdState.move(scoreState)
    }

}

//Function to check if collision occurs between dino and obstacle.
fun checkCollision(dinoRect: Rect, obstacleRect: Rect): Boolean {
    if(dinoRect.overlaps(obstacleRect))
        return true
    else
        return false

}

