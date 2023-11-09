package com.example.foodexpirationtracker.ingredient

data class Ingredient(
    val id: String? = "",
    val ingredientName: String? = "",
    val userId: String? = "",
    val keywords: List<String>? = listOf(),
    val expiryDate: String? = "",
    val notes: String? = "",
)
