package com.example.recipeapp.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipeapp.data.api.MealApiService
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.repository.RecipeRepository

class RecipeSearchPagingSource(private val apiService: MealApiService, private val query: String) : PagingSource<Int, Recipe>() {
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let {position->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize

            val response = apiService.getSearchedRecipes(query, pageSize, page)

            LoadResult.Page(
                data = response.body()!!.recipes,
                prevKey = if (page == 0) null else page-pageSize,
                nextKey = if(response.body()!!.recipes.isEmpty()) null else page+pageSize
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }

    }

}