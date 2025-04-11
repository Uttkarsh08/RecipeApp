package com.example.recipeapp.presentation.ui

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.navigation.Screens
import com.example.recipeapp.presentation.viewmodel.RecipeViewModel
import com.example.recipeapp.utils.NetworkUtil.isNetworkAvailable
import com.example.recipeapp.utils.RecipeListState
import kotlinx.coroutines.flow.Flow


@Composable
fun HomeScreen(
    viewModel: RecipeViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isOnline = remember { isNetworkAvailable(context) }

    // All recipes flow is already stateIn'd inside ViewModel
    val allRecipes = viewModel.allRecipes.collectAsLazyPagingItems()

    // Hold Flow<PagingData<Recipe>> for searched recipes
    var searchedFlow by remember { mutableStateOf<Flow<PagingData<Recipe>>?>(null) }

    // Update the searched flow when query changes
    LaunchedEffect(searchQuery, isOnline) {
        if (searchQuery.isNotEmpty()) {
            searchedFlow = viewModel.searchRecipes(searchQuery, isOnline)
        }
    }

    // Collect searchedFlow only when it's not null
    val searchedRecipes = searchedFlow?.collectAsLazyPagingItems()

    // Decide which to show
    val recipesToShow = if (searchQuery.isEmpty()) allRecipes else searchedRecipes

    Column(modifier = Modifier.fillMaxSize()) {

        TextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            placeholder = { Text("Search recipes...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Only render when recipesToShow is not null
        recipesToShow?.let { pagingItems ->
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(pagingItems.itemCount) { index ->
                    pagingItems[index]?.let { recipe ->
                        RecipeItem(recipe = recipe, onClick = {
                            navController.navigate(Screens.RecipeDetailScreen.route + "/${recipe.id}")
                        })
                    }
                }

                // Loading
                if (pagingItems.loadState.append is LoadState.Loading) {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }

                // Error
                val errorState = pagingItems.loadState.refresh as? LoadState.Error
                errorState?.let {
                    item {
                        Text(
                            text = "Error: ${it.error.localizedMessage}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

