package com.example.foodexpirationtracker.authentication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.foodexpirationtracker.HomeActivity


import com.example.foodexpirationtracker.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var createAccountInputsArray: Array<EditText>

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createAccountInputsArray = arrayOf(binding.registerEmailAddress, binding.password, binding.confirmPassword)

        auth = Firebase.auth

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.registerButton.setOnClickListener {
            register()
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
        val userEmail = createAccountInputsArray[0].text.toString().trim()
        val userPassword = createAccountInputsArray[1].text.toString().trim()
        val userConfirmPassword = createAccountInputsArray[2].text.toString().trim()

        if (userEmail.isEmpty() || userPassword.isEmpty() || userConfirmPassword.isEmpty()) {
            return false
        }

        if (userPassword != userConfirmPassword) {
            return false
        }

        return true
    }

    private fun register() {

        if (validateInput()) {
            userEmail = createAccountInputsArray[0].text.toString().trim()
            userPassword = createAccountInputsArray[1].text.toString().trim()

            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        }

    }

}