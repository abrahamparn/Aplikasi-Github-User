package com.example.mygithubuser.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mygithubuser.R

class OpeningScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening_screen)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@OpeningScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        } , 2000)
    }
}