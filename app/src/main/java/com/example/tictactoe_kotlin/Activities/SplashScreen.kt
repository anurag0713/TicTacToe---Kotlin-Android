package com.example.tictactoe_kotlin.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.tictactoe_kotlin.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, GameScreen::class.java)
            startActivity(intent)
        }, 2000)
    }
}