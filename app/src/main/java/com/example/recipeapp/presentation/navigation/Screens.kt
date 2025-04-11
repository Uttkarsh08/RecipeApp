package com.example.recipeapp.presentation.navigation

sealed class Screens(val route : String) {
    data object HomeScreen : Screens("homeScreen")
    data object RecipeDetailScreen : Screens("recipeDetailScreen")
}