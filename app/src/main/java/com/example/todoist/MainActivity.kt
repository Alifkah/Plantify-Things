package com.example.todoist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        // Cek status login via SharedPreferences
        val loginStatusPref = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE)
        val isLoggedIn = loginStatusPref.getBoolean("IS_LOGGED_IN", false)

        if (!isLoggedIn || firebaseAuth.currentUser == null) {
            navigateToLogin()
            return
        }

        // Ambil username dari SharedPreferences
        val username = loginStatusPref.getString("CURRENT_USER", "User")

        val welcomeText = findViewById<TextView>(R.id.tvUsername)
        welcomeText.text = "Welcome, $username!"

        val logoutButton = findViewById<Button>(R.id.buttonLogout)
        logoutButton.setOnClickListener {
            // Logout dari Firebase
            firebaseAuth.signOut()

            // Clear local login status
            with(loginStatusPref.edit()) {
                putBoolean("IS_LOGGED_IN", false)
                putString("CURRENT_USER", "")
                apply()
            }

            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
