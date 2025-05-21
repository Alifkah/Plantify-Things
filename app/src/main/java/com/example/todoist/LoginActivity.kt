package com.example.todoist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoist.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listener for login button
        binding.buttonLogin.setOnClickListener {
            loginUser()
        }

        // Set up click listener for register text
        binding.textViewRegister.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Set up back button click listener
        binding.imageViewLogo.setOnClickListener {
            finish()
        }
    }

    private fun loginUser() {
        val username = binding.editTextUsername.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        // Validate inputs
        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is required"
            binding.editTextUsername.requestFocus()
            return
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is required"
            binding.editTextPassword.requestFocus()
            return
        }

        // Check credentials against SharedPreferences
        val sharedPref = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE)
        val savedPassword = sharedPref.getString(username, null)

        if (savedPassword != null && savedPassword == password) {
            // Login success
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

            // Store login status
            val loginStatusPref = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE)
            with(loginStatusPref.edit()) {
                putBoolean("IS_LOGGED_IN", true)
                putString("CURRENT_USER", username)
                apply()
            }

            // Navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } else {
            // Login failed
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }
}