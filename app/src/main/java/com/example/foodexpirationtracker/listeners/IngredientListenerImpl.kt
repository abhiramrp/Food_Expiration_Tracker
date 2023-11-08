package com.example.foodexpirationtracker.listeners

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexpirationtracker.ingredient.Ingredient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class IngredientListenerImpl(val ingredientList: RecyclerView, val callback: HomeCallback?) : IngredientListener {
    private val firebaseDB = Firebase.firestore
    private val userId = Firebase.auth.currentUser?.uid
    override fun onLayoutClick(item: Ingredient?) {
        AlertDialog.Builder(ingredientList.context)
            .show()
    }


}