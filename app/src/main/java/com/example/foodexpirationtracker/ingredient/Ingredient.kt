package com.example.foodexpirationtracker.ingredient

data class Ingredient(
    val ingredientId: String? = "",
    val ingredientName: String? = "",
    val expiryDate: String? = "",
    val notes: String? = "",
)
