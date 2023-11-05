package com.example.foodexpirationtracker.ingredient

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodexpirationtracker.R
import com.example.foodexpirationtracker.databinding.ItemIngredientBinding
import java.util.ArrayList

class ListAdapter (val userId: String, val ingredients: ArrayList<Ingredient>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var binding = ItemIngredientBinding.bind(v)
        fun bind(userId: String, ingredient: Ingredient, listener: IngredientListener?) {
            binding.ingredientTitle.text = ingredient.ingredientName
            binding.ingredientDate.text = ingredient.expiryDate

            if (ingredient.notes.isNullOrEmpty()) {
                binding.ingredientNotes.visibility = View.GONE
            } else {
                binding.ingredientNotes.text = ingredient.notes
                binding.ingredientNotes.visibility = View.VISIBLE
            }

        }

    }

    private var listener: IngredientListener? = null

    fun setListener(listener: IngredientListener?) {
        this.listener = listener
    }

    fun updateIngredients(newIngredients: List<Ingredient>) {
        ingredients.clear()
        ingredients.addAll(newIngredients)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userId, ingredients[position], listener)
    }

}