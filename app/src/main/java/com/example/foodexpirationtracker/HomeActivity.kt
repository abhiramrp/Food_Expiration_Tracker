package com.example.foodexpirationtracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.foodexpirationtracker.authentication.LoginActivity
import com.example.foodexpirationtracker.databinding.ActivityHomeBinding
import com.example.foodexpirationtracker.fragments.ActivityFragment
import com.example.foodexpirationtracker.fragments.HomeFragment
import com.example.foodexpirationtracker.fragments.SearchFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


    }

    fun logout(v: View) {
        auth.signOut()
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            startActivity(LoginActivity.newIntent(this))
            finish()
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }


}