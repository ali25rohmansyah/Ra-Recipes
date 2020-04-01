package com.example.ra_recipes.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ra_recipes.network.DetailNutritions
import com.example.ra_recipes.network.Ingredients

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var image: String,
    var ingredients: List<Ingredients>,
    var instruction: String,
    var nutritions: List<DetailNutritions>
)