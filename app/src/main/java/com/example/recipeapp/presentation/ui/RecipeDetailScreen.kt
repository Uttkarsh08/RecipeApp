package com.example.recipeapp.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.viewmodel.RecipeViewModel
import com.example.recipeapp.utils.RecipeState


@Composable
fun RecipeDetailScreen(viewModel: RecipeViewModel, navController: NavController, recipeId: String){

    val recipeState = viewModel.recipeState.collectAsState().value
    val context = LocalContext.current


    LaunchedEffect(key1 = recipeId) {
        viewModel.fetchRecipeById(recipeId)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        IconButton(onClick = {navController.navigateUp()}) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
        when(recipeState){
            is RecipeState.Error ->{
                Toast.makeText(context, recipeState.error, Toast.LENGTH_SHORT).show()
            }

            RecipeState.Loading -> CircularProgressIndicator()

            is RecipeState.Success -> {
                val recipe = recipeState.data

                    AsyncImage(
                        model = recipe.image,
                        contentDescription = "Dish Image",
                        modifier = Modifier.fillMaxWidth()
                            .height(200.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = recipe.name, style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ))

                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                                .fillMaxWidth()
                        ) {

                            Text(text = "Instructions", style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            ))

                            Spacer(modifier = Modifier.height(4.dp))

                            val instructions  = recipe.instructions
                            instructions.forEachIndexed { index, instruction ->
                                Text(text = "Step ${index + 1}: $instruction")
                            }

                        }
                    }
            }

        }
    }
}