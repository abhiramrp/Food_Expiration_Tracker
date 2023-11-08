package com.example.foodexpirationtracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.foodexpirationtracker.authentication.LoginActivity
import com.example.foodexpirationtracker.databinding.ActivityHomeBinding
import com.example.foodexpirationtracker.fragments.*
import com.example.foodexpirationtracker.ingredient.IngredientActivity
import com.example.foodexpirationtracker.listeners.HomeCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity(), HomeCallback {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PageAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    private var userId: String? = null

    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var activityFragment: ActivityFragment

    private lateinit var currentFragment: IngredientFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        userId = auth.currentUser?.uid

        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        activityFragment = ActivityFragment()

        currentFragment = homeFragment


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

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchFragment.getQuery(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchFragment.getQuery(newText.toString())
                return true
            }
        })

    }

    // BUTTON FUNCTIONS

    fun addIngedient(v: View) {
        startActivity(IngredientActivity.newIntent(this, userId))
    }

    private fun checkLogin() {
        if (auth.currentUser == null) {
            startActivity(LoginActivity.newIntent(this))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        checkLogin()
    }

    override fun onResume() {
        super.onResume()
        checkLogin()
    }


    inner class PageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 3

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> homeFragment
                1 -> searchFragment
                else -> activityFragment
            }
        }

    }



    companion object {
        fun newIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

    override fun onRefresh() {
        currentFragment.updateList()
    }


}