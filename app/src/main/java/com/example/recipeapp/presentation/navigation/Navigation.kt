package com.example.recipeapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeapp.presentation.ui.HomeScreen
import com.example.recipeapp.presentation.ui.RecipeDetailScreen
import com.example.recipeapp.presentation.viewmodel.RecipeViewModel

@Composable
fun Navigation(viewModel: RecipeViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "homeScreen"){
        composable(Screens.HomeScreen.route) {
            HomeScreen(viewModel, navController)
        }

        composable(route = Screens.RecipeDetailScreen.route + "/{recipeId}",
            arguments = listOf(
                navArgument("recipeId"){type = NavType.StringType}
            )
        ) { backstackEntry ->
            val recipeId = backstackEntry.arguments?.getString("recipeId")
            RecipeDetailScreen(viewModel, navController, recipeId.toString())
        }
    }
}