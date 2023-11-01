package com.example.foodexpirationtracker.authentication

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.foodexpirationtracker.HomeActivity


import com.example.foodexpirationtracker.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var userEmail: EditText
    private lateinit var userPassword : EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        userEmail = binding.editEmail
        userPassword = binding.editPassword
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null) {
            startActivity(HomeActivity.newIntent(this))
            finish()
        }

    }

    private fun validateInput(): Boolean {
        if(userEmail.text.toString().isNullOrEmpty()) {
            userEmail.error = "Email required"
            return false
        }

        if(userPassword.text.toString().isNullOrEmpty()) {
            userPassword.error = "Password required"
            return false
        }

        return true

    }

    fun registerUser(v: View) {
        if (validateInput()) {
            auth.createUserWithEmailAndPassword(userEmail.text.toString().trim(), userPassword.text.toString().trim())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(HomeActivity.newIntent(this))
                        finish()
                    } else {
                        Log.w(ContentValues.TAG, "Register:failure", task.exception)
                        Toast.makeText(this, "Register error: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    fun loginUser(v: View) {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, RegisterActivity::class.java)
    }

}