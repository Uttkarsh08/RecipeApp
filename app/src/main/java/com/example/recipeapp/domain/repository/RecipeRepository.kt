package com.example.recipeapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.recipeapp.data.api.MealApiService
import com.example.recipeapp.data.pagination.RecipePagingSource
import com.example.recipeapp.data.pagination.RecipeSearchPagingSource
import com.example.recipeapp.domain.model.ApiResponseModel
import com.example.recipeapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RecipeRepository(private val apiService: MealApiService) {

//    fun getRecipes(): Flow<PagingData<Recipe>> {
//        return Pager(
//            config = PagingConfig(10),
//            pagingSourceFactory = {RecipePagingSource(apiService)}
//        ).flow
//    }

    suspend fun getRecipeById(recipeId: String): Response<Recipe> {
        return apiService.getRecipeById(recipeId)
    }

    fun getSearchedAndAllRecipes(query: String): Flow<PagingData<Recipe>> {
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                if(query.isEmpty()){
                    RecipePagingSource(apiService)
                }else{
                    RecipeSearchPagingSource(apiService, query)
                }
            }
        ).flow
    }
}