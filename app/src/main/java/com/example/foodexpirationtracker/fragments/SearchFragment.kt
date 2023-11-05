package com.example.foodexpirationtracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodexpirationtracker.R
import com.example.foodexpirationtracker.ingredient.ListAdapter
import com.google.firebase.firestore.FirebaseFirestore


class SearchFragment : IngredientFragment() {

    private var ingredientAdapter: ListAdapter ?= null
    private lateinit var firebaseDb : FirebaseFirestore
    private var userId: String? = null


    override fun updateList() {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}