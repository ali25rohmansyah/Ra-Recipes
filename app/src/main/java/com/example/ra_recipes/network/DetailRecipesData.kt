package com.example.ra_recipes.network

import com.squareup.moshi.Json


data class DetailRecipesData (
    val title: String,
    val image: String,
    val instructions: String?,
    @Json(name= "extendedIngredients") val ingredients: List<Ingredients>
)
