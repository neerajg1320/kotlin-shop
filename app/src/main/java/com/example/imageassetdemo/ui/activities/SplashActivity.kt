package com.example.imageassetdemo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.imageassetdemo.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        @Suppress("DEPRECATION")
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
            finish()
        }, 1000)
    }
}