package com.example.todoist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding)

        val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)
        val tvSkip = findViewById<TextView>(R.id.tvOnboarding)

        // Klik tombol Get Started
        btnGetStarted.setOnClickListener {
            goToStarScreen()
        }

        // Klik tulisan Skip
        tvSkip.setOnClickListener {
            goToStarScreen()
        }
    }

    private fun goToStarScreen() {
        val intent = Intent(this, StartScreenActivity::class.java)
        startActivity(intent)
        finish() // Supaya tidak bisa kembali ke onboarding
    }
}
