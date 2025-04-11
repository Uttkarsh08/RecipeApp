package com.example.recipeapp.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.recipeapp.presentation.navigation.Screens
import com.example.recipeapp.presentation.viewmodel.RecipeViewModel
import com.example.recipeapp.utils.RecipeListState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: RecipeViewModel, navController: NavController){

    val recipes = viewModel.pagedRecipes.collectAsLazyPagingItems()
    var searchQuery = viewModel.searchQuery.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Recipes",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                                viewModel.updateSearchQuery(searchQuery)
                            },
                            placeholder = { Text("Search recipes...") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp),
                        )
                    }
                }
            )
        }
    ) {pv->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(pv),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                LazyColumn {
                    items(recipes.itemCount) { index ->
                        val recipe = recipes[index]
                        if (recipe != null) {
                            RecipeItem(recipe, onClick = {
                                navController.navigate(Screens.RecipeDetailScreen.route+"/${recipe.id}")
                            })
                        }
                    }
                    recipes.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    CircularProgressIndicator()
                                }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val error = loadState.refresh as LoadState.Error
                                item {
                                    Text("Error loading data: ${error.error.message}")
                                }
                            }
                        }
                    }

                }
            }
    }
}