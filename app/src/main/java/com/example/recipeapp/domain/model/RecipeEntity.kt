package com.example.recipeapp.domain.model

import android.widget.RemoteViews.DrawInstructions
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val image: String,
    val cuisine: String,
    val difficulty: String,
    val prepTimeMinutes: Int,
    val rating: Double,

)
