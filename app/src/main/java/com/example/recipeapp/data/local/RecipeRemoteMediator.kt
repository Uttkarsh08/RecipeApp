package com.example.recipeapp.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.recipeapp.data.api.RecipeApiService
import com.example.recipeapp.domain.model.RecipeEntity
import com.example.recipeapp.domain.model.toEntity

@OptIn(ExperimentalPagingApi::class)
class RecipeRemoteMediator(
    private val apiService: RecipeApiService,
    private val roomDB: RecipeDB
) : RemoteMediator<Int, RecipeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RecipeEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null){
                        0
                    }else{
                        (state.pages.sumOf { it.data.size }) / state.config.pageSize
                    }
                }
            }

            val response  = apiService.getRecipes(
                limit = state.config.pageSize,
                skip = loadKey * state.config.pageSize
            )

            val recipes = response.body()?.recipes.orEmpty()
            val entities = recipes.map { it.toEntity() }

            roomDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    roomDB.recipeDao().clearAll()
                }

                roomDB.recipeDao().insertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = recipes.isEmpty())


        }catch (e: Exception){
            MediatorResult.Error(e)
        }
    }
}