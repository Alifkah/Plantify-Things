package com.example.todoist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoist.ui.theme.TodoistTheme
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if user is logged in
        val loginStatusPref = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE)
        val isLoggedIn = loginStatusPref.getBoolean("IS_LOGGED_IN", false)

        if (!isLoggedIn) {
            // If not logged in, redirect to LoginActivity
            navigateToLogin()
            return
        }

        // Get current username
        val username = loginStatusPref.getString("CURRENT_USER", "User")

        // Display welcome message
        val welcomeText = findViewById<TextView>(R.id.textViewWelcome)
        welcomeText.text = "Welcome, $username!"

        // Set up logout button
        val logoutButton = findViewById<Button>(R.id.buttonLogout)
        logoutButton.setOnClickListener {
            // Clear login status
            with(loginStatusPref.edit()) {
                putBoolean("IS_LOGGED_IN", false)
                putString("CURRENT_USER", "")
                apply()
            }

            // Navigate to login
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