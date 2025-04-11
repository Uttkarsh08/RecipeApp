//package com.example.recipeapp.data.pagination
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.recipeapp.data.api.RecipeApiService
//import com.example.recipeapp.domain.model.Recipe
//
//
//class RecipePagingSource(private val apiService: RecipeApiService) : PagingSource<Int, Recipe>(){
//    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
//        return state.anchorPosition?.let {position->
//            val page = state.closestPageToPosition(position)
//            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
//
//        return try {
//            val page = params.key ?: 0
//            val pageSize = params.loadSize
//            val response = apiService.getRecipes(pageSize, page)
//            val recipes = response.body()?.recipes ?: emptyList()
//
//            LoadResult.Page(
//                data = recipes,
//                prevKey = if(page == 0) null else page-pageSize,
//                nextKey = if(recipes.isEmpty()) null else page+pageSize
//            )
//
//        }catch (e: Exception){
//            LoadResult.Error(e)
//        }
//
//
//
//    }
//
//}