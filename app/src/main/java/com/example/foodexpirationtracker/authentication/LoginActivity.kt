package com.example.foodexpirationtracker.authentication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.foodexpirationtracker.HomeActivity
import com.example.foodexpirationtracker.R
import com.example.foodexpirationtracker.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var loginInputsArray: Array<EditText>

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        loginInputsArray = arrayOf(binding.loginEmailAddress, binding.password)

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener {
            login()
        }

    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

    }

    private fun validateInput(): Boolean {
        val userEmail = loginInputsArray[0].text.toString().trim()
        val userPassword = loginInputsArray[1].text.toString().trim()

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            return false
        }

        return true
    }

    private fun login() {

        if (validateInput()) {
            userEmail = loginInputsArray[0].text.toString().trim()
            userPassword = loginInputsArray[1].text.toString().trim()

            auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Log.w(ContentValues.TAG, "loginWithEmail:failure", task.exception)
                    }
                }
        }

    }




}