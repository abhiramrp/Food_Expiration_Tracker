package com.example.foodexpirationtracker.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodexpirationtracker.activities.HomeActivity
import com.example.foodexpirationtracker.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "RegisterActivity"
    }

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        auth = Firebase.auth

        setContentView(binding.root)

        binding.loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerButton.setOnClickListener {
            val email = binding.editEmailAddress.text.toString()
            val password = binding.editPassword.text.toString()
            val retypePassword = binding.editRetypePassword.text.toString()

            if (password == retypePassword) {
                register(email, password)
            } else {
                Toast.makeText(
                    baseContext,
                    "Passwords must match",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "registerSuccess")
                    goToHome()
                } else {
                    Log.w(TAG, "registerFailure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}