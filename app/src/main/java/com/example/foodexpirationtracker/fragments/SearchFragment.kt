package com.example.foodexpirationtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodexpirationtracker.DATA_INGREDIENTS
import com.example.foodexpirationtracker.DATA_INGREDIENTS_KEYWORDS
import com.example.foodexpirationtracker.databinding.FragmentSearchBinding
import com.example.foodexpirationtracker.ingredient.Ingredient
import com.example.foodexpirationtracker.listeners.IngredientListener
import com.example.foodexpirationtracker.ingredient.ListAdapter
import com.example.foodexpirationtracker.listeners.IngredientListenerImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SearchFragment : IngredientFragment() {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var searchQuery: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = FirebaseAuth.getInstance().currentUser?.uid

        listener = IngredientListenerImpl(binding.ingredientList, callback)

        ingredientAdapter = ListAdapter(userId!!, arrayListOf())
        ingredientAdapter?.setListener(listener)
        binding.ingredientList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ingredientAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        binding.ingredientList?.adapter = ingredientAdapter

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            updateList()
        }

    }

    fun getQuery(s: String) {
        searchQuery = s
        updateList()
    }

    override fun updateList() {
        binding.ingredientList?.visibility = View.GONE
        firebaseDb.collection(DATA_INGREDIENTS).whereArrayContains(DATA_INGREDIENTS_KEYWORDS, searchQuery.trim()).get()
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