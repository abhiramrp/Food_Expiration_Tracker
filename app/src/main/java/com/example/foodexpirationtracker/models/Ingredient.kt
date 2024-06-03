package com.example.foodexpirationtracker.models

data class Ingredient(val user_id: String = "",
                      val title: String = "",
                      val expiry_date: String = "",
                      var product_id: String = "")
