package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.presentation.navigation.Navigation
import com.example.recipeapp.presentation.ui.HomeScreen
import com.example.recipeapp.presentation.viewmodel.RecipeViewModel
import com.example.recipeapp.presentation.viewmodel.RecipeViewModelFactory
import com.example.recipeapp.ui.theme.RecipeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val repository = (application as RecipeApplication).repository
            val viewModel: RecipeViewModel = viewModel(factory = RecipeViewModelFactory(repository))

            Navigation(viewModel)
        }
    }
}
