package com.example.foodexpirationtracker.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.foodexpirationtracker.ingredient.ListAdapter
import com.example.foodexpirationtracker.listeners.HomeCallback
import com.example.foodexpirationtracker.listeners.IngredientListenerImpl
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

abstract class IngredientFragment : Fragment() {

    protected var ingredientAdapter: ListAdapter?= null
    protected var firebaseDb = Firebase.firestore
    protected var userId : String? = null
    protected var listener: IngredientListenerImpl?=null
    protected var callback: HomeCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is HomeCallback) {
            callback = context
        } else {
            throw RuntimeException(context.toString() + " must implement HomeCallback")
        }
    }


    abstract fun updateList()

    override fun onResume() {
        super.onResume()
        updateList()
    }

}
