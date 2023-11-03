package com.example.foodexpirationtracker

import android.app.assist.AssistContent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.foodexpirationtracker.authentication.LoginActivity
import com.example.foodexpirationtracker.databinding.ActivityHomeBinding
import com.example.foodexpirationtracker.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.IllegalArgumentException
import java.util.ArrayList


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PageAdapter
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        viewPager = binding.viewPager
        adapter = PageAdapter(this)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.itemCount

        bottomNavigationView = binding.bottomNavBar
        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.homeItem -> viewPager.currentItem = 0
                R.id.searchItem -> viewPager.currentItem = 1
                R.id.profileItem -> viewPager.currentItem = 2
            }

            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigationView.menu.getItem(position).isChecked = true
            }
        })


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


    inner class PageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 3

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> HomeFragment()
                1 -> SearchFragment()
                else -> ActivityFragment()
            }
        }

    }



    companion object {
        fun newIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }


}