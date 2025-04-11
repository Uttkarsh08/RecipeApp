package com.example.recipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeapp.domain.model.RecipeEntity

@Database(
    entities = [RecipeEntity::class],
    version = 2,
    exportSchema = false
)
abstract class RecipeDB: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

}