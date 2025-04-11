package com.example.recipeapp.domain.model

data class ApiResponseModel(
    val limit: Int,
    val recipes: List<Recipe>,
    val skip: Int,
    val total: Int
)