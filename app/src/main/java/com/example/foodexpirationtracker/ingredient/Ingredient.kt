package com.example.foodexpirationtracker.ingredient

data class Ingredient(
    val ingredientId: String? = "",
    val ingredientName: String? = "",
    val text: String? = "",
    val expiryDate: Long? = 0,
    val timestamp: Long? = 0,
)
