package com.example.foodexpirationtracker.activities.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.foodexpirationtracker.activities.HomeActivity
import com.example.foodexpirationtracker.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        auth = Firebase.auth

        setContentView(binding.root)

        binding.registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            val email = binding.editEmailAddress.text.toString()
            val password = binding.editPassword.text.toString()

            login(email, password)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "logInSuccess")
                    goToHome()
                } else {
                    Log.w(TAG, "logInFailure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Log In Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

