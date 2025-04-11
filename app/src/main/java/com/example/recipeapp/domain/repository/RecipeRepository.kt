package com.example.recipeapp.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.recipeapp.data.api.RecipeApiService
import com.example.recipeapp.data.local.RecipeDB
import com.example.recipeapp.data.local.RecipeRemoteMediator
import com.example.recipeapp.data.pagination.RecipeSearchPagingSource
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.model.RecipeEntity
import com.example.recipeapp.domain.model.toRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class RecipeRepository(private val apiService: RecipeApiService,
    private val database: RecipeDB
    ) {


    suspend fun getRecipeById(recipeId: String): Response<Recipe> {
        return apiService.getRecipeById(recipeId)
    }


    @OptIn(ExperimentalPagingApi::class)
    fun getCachedRecipes(): Flow<PagingData<Recipe>> {
        return Pager(
            config = PagingConfig(10),
            remoteMediator = RecipeRemoteMediator(apiService, database),
            pagingSourceFactory = {
                database.recipeDao().getAllRecipes()
            }
        ).flow.map { pagingData->
            pagingData.map { it.toRecipe() }
        }
    }

    fun searchRecipesOnline(query: String): Flow<PagingData<Recipe>>{
        return  Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {RecipeSearchPagingSource(apiService, query)}
        ).flow
    }

    fun searchRecipesOffline(query: String): Flow<PagingData<Recipe>>{
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                database.recipeDao().searchRecipes("%$query%")
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toRecipe() }
        }
    }
}