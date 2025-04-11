package com.example.recipeapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.repository.RecipeRepository
import com.example.recipeapp.utils.NetworkUtil.isNetworkAvailable
import com.example.recipeapp.utils.RecipeListState
import com.example.recipeapp.utils.RecipeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _recipeState = MutableStateFlow<RecipeState>(RecipeState.Loading)
    val recipeState: StateFlow<RecipeState> = _recipeState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val allRecipes = repository.getCachedRecipes().cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun searchRecipes(query: String, isOnline: Boolean): Flow<PagingData<Recipe>> {
        return if(isOnline){
            repository.searchRecipesOnline(query)
        }else{
            repository.searchRecipesOffline(query)
        }.cachedIn(viewModelScope)
    }


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun fetchRecipeById(recipeId: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if(isNetworkAvailable(context)){
                val response = repository.getRecipeById(recipeId)
                _recipeState.value = if (response.isSuccessful && response.body() != null) {
                    RecipeState.Success(response.body()!!)
                } else {
                    RecipeState.Error(response.message())
                }
            }else{
                _recipeState.value = RecipeState.Error("No Internet Connection")
            }
        }
    }
}
