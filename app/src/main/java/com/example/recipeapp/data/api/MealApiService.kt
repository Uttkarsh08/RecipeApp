package com.example.recipeapp.data.api

import com.example.recipeapp.domain.model.ApiResponseModel
import com.example.recipeapp.domain.model.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApiService {

    @GET("recipes")
    suspend fun getRecipes(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Response<ApiResponseModel>


    @GET("recipes/{recipeId}")
    suspend fun getRecipeById(
        @Path("recipeId") recipeId: String
    ): Response<Recipe>

    @GET("recipes/search")
    suspend fun getSearchedRecipes(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Response<ApiResponseModel>


}