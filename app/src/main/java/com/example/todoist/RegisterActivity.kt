package com.example.todoist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoist.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.buttonLogin.setOnClickListener {
            registerUser()
        }

        binding.textViewLogin.setOnClickListener {
            finish()
        }

        binding.imageViewLogo.setOnClickListener {
            finish()
        }
    }

    private fun registerUser() {
        val username = binding.editTextUsername.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()

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

        if (password.length < 6) {
            binding.editTextPassword.error = "Password should be at least 6 characters"
            binding.editTextPassword.requestFocus()
            return
        }

        // Gunakan username untuk membuat email dummy: username@example.com
        val email = "$username@example.com"

        // Cek apakah username sudah digunakan
        firestore.collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    binding.editTextUsername.error = "Username already exists"
                    binding.editTextUsername.requestFocus()
                    return@addOnSuccessListener
                }

                // Buat akun di Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val uid = it.user?.uid ?: return@addOnSuccessListener

                        // Simpan username & email di Firestore
                        val userMap = hashMapOf(
                            "username" to username,
                            "email" to email
                        )

                        firestore.collection("users")
                            .document(uid)
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error checking username: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}
