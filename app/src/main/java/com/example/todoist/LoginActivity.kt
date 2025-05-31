package com.example.todoist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoist.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonLogin.setOnClickListener {
            loginUser()
        }

        binding.textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.imageViewLogo.setOnClickListener {
            finish()
        }
    }

    private fun loginUser() {
        val username = binding.editTextUsername.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

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

        // Buat email dari username
        val email = "$username@example.com"

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Simpan status login
                val loginStatusPref = getSharedPreferences("LOGIN_STATUS", MODE_PRIVATE)
                with(loginStatusPref.edit()) {
                    putBoolean("IS_LOGGED_IN", true)
                    putString("CURRENT_USER", username)
                    apply()
                }

                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                // Navigasi ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Login failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
