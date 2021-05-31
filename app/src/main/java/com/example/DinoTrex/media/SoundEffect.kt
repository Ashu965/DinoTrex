package com.example.DinoTrex.media

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.example.DinoTrex.R

class SoundEffect(val context: Context){
    private var soundPool: SoundPool
    private var jumpSound : Int
    private var gameEndSound : Int
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
            soundPool =
                SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build()
        } else {
            soundPool = SoundPool(1, AudioManager.STREAM_MUSIC,0)
        }
         jumpSound = soundPool.load(context, R.raw.sound,1)
         gameEndSound = soundPool.load(context, R.raw.ending,1)
    }
    fun playJumpSound(){
        soundPool.play(jumpSound,1F,1F,0,0,1F)
    }
    fun playGameEndSound(){
        soundPool.play(gameEndSound,1F,1F,0,0,1F)
    }
}