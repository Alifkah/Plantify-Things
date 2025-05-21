package com.example.todoist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoist.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listener for login button (which is used for registration in this screen)
        binding.buttonLogin.setOnClickListener {
            registerUser()
        }

        // Set up click listener for login text
        binding.textViewLogin.setOnClickListener {
            // Navigate back to LoginActivity
            finish()
        }

        // Set up back button click listener
        binding.imageViewLogo.setOnClickListener {
            finish()
        }
    }

    private fun registerUser() {
        val username = binding.editTextUsername.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

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

        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPassword.error = "Please confirm your password"
            binding.editTextConfirmPassword.requestFocus()
            return
        }

        if (password != confirmPassword) {
            binding.editTextConfirmPassword.error = "Passwords do not match"
            binding.editTextConfirmPassword.requestFocus()
            return
        }

        // Password length validation
        if (password.length < 6) {
            binding.editTextPassword.error = "Password should be at least 6 characters"
            binding.editTextPassword.requestFocus()
            return
        }

        // Store credentials in SharedPreferences
        val sharedPref = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE)

        // Check if username already exists
        if (sharedPref.contains(username)) {
            binding.editTextUsername.error = "Username already exists"
            binding.editTextUsername.requestFocus()
            return
        }

        // Save the new user
        with(sharedPref.edit()) {
            putString(username, password)
            apply()
        }

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()

        // Navigate back to login screen
        finish()
    }
}