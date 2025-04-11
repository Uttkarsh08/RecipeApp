package com.example.recipeapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.domain.model.RecipeEntity


@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("DELETE FROM recipes")
    suspend fun clearAll()

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): PagingSource<Int, RecipeEntity>

    @Query("SELECT * FROM recipes WHERE name LIKE :query")
    fun searchRecipes(query: String): PagingSource<Int, RecipeEntity>
}