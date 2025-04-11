package com.example.recipeapp

import android.app.Application
import androidx.room.Room
import com.example.recipeapp.data.api.RecipeApiService
import com.example.recipeapp.data.api.RetrofitHelper
import com.example.recipeapp.data.local.RecipeDB
import com.example.recipeapp.domain.repository.RecipeRepository

class RecipeApplication: Application() {
    lateinit var repository: RecipeRepository

    override fun onCreate() {
        initialize()
        super.onCreate()
    }

    fun initialize(){
        val service: RecipeApiService = RetrofitHelper.getInstance().create(RecipeApiService::class.java)
        val database = Room.databaseBuilder(applicationContext, RecipeDB::class.java, "recipe_database").fallbackToDestructiveMigration().build()
        repository = RecipeRepository(service, database)
    }
}