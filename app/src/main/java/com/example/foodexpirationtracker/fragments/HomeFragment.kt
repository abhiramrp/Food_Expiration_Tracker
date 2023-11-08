package com.example.foodexpirationtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodexpirationtracker.DATA_INGREDIENTS
import com.example.foodexpirationtracker.databinding.FragmentHomeBinding
import com.example.foodexpirationtracker.ingredient.Ingredient
import com.example.foodexpirationtracker.listeners.IngredientListener
import com.example.foodexpirationtracker.ingredient.ListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeFragment : IngredientFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = FirebaseAuth.getInstance().currentUser?.uid

        ingredientAdapter = ListAdapter(userId!!, arrayListOf())
        ingredientAdapter?.setListener(listener)
        binding.ingredientList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ingredientAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun updateList() {
        binding.ingredientList?.visibility = View.GONE
        firebaseDb.collection(DATA_INGREDIENTS).get()
            .addOnSuccessListener { list ->
                binding.ingredientList?.visibility = View.VISIBLE
                val ingredients = arrayListOf<Ingredient>()
                for (d in list.documents) {
                    val ingredient = d.toObject(Ingredient::class.java)
                    ingredient?.let { ingredients.add(it) }
                }
                val sortedIngredients = ingredients.sortedWith(compareByDescending { it.expiryDate })
                ingredientAdapter?.updateIngredients(sortedIngredients)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }



}