package com.example.recipeapp

import android.app.Application
import com.example.recipeapp.data.api.MealApiService
import com.example.recipeapp.data.api.RetrofitHelper
import com.example.recipeapp.domain.repository.RecipeRepository

class RecipeApplication: Application() {
    lateinit var repository: RecipeRepository

    override fun onCreate() {
        initialize()
        super.onCreate()
    }

    fun initialize(){
        val service: MealApiService = RetrofitHelper.getInstance().create(MealApiService::class.java)
        repository = RecipeRepository(service)
    }
}