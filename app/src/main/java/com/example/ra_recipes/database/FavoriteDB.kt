package com.example.ra_recipes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Favorite::class])
abstract class FavoriteDB: RoomDatabase(){
    abstract fun FavoriteDAO(): FavoriteDAO

    companion object{
        @Volatile
        private var INSTANCE: FavoriteDB? = null

        fun getInstance(context: Context): FavoriteDB{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDB::class.java,
                        "favorite_db").fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return  instance
            }
        }
    }
}