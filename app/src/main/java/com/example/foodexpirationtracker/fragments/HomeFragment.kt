package com.example.foodexpirationtracker.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodexpirationtracker.HomeActivity
import com.example.foodexpirationtracker.R


class HomeFragment : IngredientFragment() {
    override fun updateList() {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    companion object {
        fun newIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

}