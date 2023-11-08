package com.example.foodexpirationtracker.listeners

import com.example.foodexpirationtracker.ingredient.Ingredient

interface IngredientListener {
    fun onLayoutClick(item: Ingredient?)
}