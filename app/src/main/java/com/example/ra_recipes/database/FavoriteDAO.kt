package com.example.ra_recipes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDAO{
    @Query("SELECT * FROM Favorite")
    fun loadFavorite(): LiveData<List<Favorite>>

    @Insert
    fun insert(favoriteDB: Favorite)

    @Delete
    fun delete(favoriteDB: Favorite)

}