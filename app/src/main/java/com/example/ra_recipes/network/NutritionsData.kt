package com.example.ra_recipes.network

data class NutritionsData(
    val bad: List<DetailNutritions>,
    val good: List<DetailNutritions>
)