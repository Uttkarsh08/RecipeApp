package com.example.recipeapp.domain.model

import com.example.recipeapp.R

fun Recipe.toEntity(): RecipeEntity{
    return RecipeEntity(
        id = id,
        name = name,
        image = image,
        cuisine = cuisine,
        difficulty = difficulty,
        prepTimeMinutes = prepTimeMinutes,
        rating = rating
    )
}

fun RecipeEntity.toRecipe(): Recipe{
    return Recipe(
        id = id,
        name = name,
        instructions = emptyList(),
        caloriesPerServing = 0,
        cookTimeMinutes = 0,
        cuisine = cuisine,
        difficulty = difficulty,
        image = image,
        ingredients = emptyList(),
        mealType = emptyList(),
        prepTimeMinutes = prepTimeMinutes,
        rating = rating,
        reviewCount = 0,
        servings = 0,
        tags = emptyList(),
        userId = 0
    )
}