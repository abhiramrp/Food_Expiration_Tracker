package com.example.foodexpirationtracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    private var sectionPageAdapter: SectionPageAdapter? = null

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val activityFragment = ActivityFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        sectionPageAdapter = SectionPageAdapter(supportFragmentManager)

        container.adapter = sectionPageAdapter

    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


    inner class SectionPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> homeFragment
                1 -> searchFragment
                else -> activityFragment
            }
        }

        override fun getCount() = 3

    }

    companion object {
        fun newIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }


}