package com.example.DinoTrex

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameOver(modifier: Modifier = Modifier, isGameOver: Boolean = true) {
    Column(modifier = modifier) {
        Text(
            text = if (isGameOver) "GAME OVER" else "",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            letterSpacing = 5.sp,
            style = TextStyle(
                color = MaterialTheme.colors.onError,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        if (isGameOver) {
            Image(
                modifier = modifier
                    .size(70.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.replay),
                contentDescription = "A Rocket"
            )
        }
    }
}