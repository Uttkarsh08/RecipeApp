package com.example.recipeapp.utils

import com.example.recipeapp.domain.model.Recipe
import java.lang.Error

sealed class RecipeState{
    data object Loading : RecipeState()
    data class Success(val data: Recipe): RecipeState()
    data class Error(val error: String) : RecipeState()
}
