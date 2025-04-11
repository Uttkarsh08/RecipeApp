package com.example.recipeapp.utils

import androidx.paging.PagingData
import com.example.recipeapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

sealed class RecipeListState {
    data object Loading : RecipeListState()
    data class Success(val recipes: Flow<PagingData<Recipe>>) : RecipeListState()
    data class Error(val message: String) : RecipeListState()
    data object Searching: RecipeListState()

}