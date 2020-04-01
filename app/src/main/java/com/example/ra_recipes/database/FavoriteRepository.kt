package com.example.ra_recipes.database

class FavoriteRepository (private val favoriteDAO: FavoriteDAO){
    val allFavorite = favoriteDAO.loadFavorite()
}