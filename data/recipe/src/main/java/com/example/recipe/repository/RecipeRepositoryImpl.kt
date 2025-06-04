package com.example.recipe.repository

import com.example.recipe.domain.Recipe
import com.example.recipe.domain.RecipeRepository
import com.example.recipe.repository.source.RemoteRecipeDataSource
import io.reactivex.Single

internal class RecipeRepositoryImpl(
    private val remoteRecipeDataSource: RemoteRecipeDataSource
) : RecipeRepository {
    override fun getRecipes(limit: Int?): Single<List<Recipe>> {
        return remoteRecipeDataSource.getRecipes(limit)
    }

    override fun getRecipeById(id: Int): Single<Recipe> {
        return remoteRecipeDataSource.getRecipeById(id)
    }

    override fun getRecipeByCategory(categoryId: Int): Single<List<Recipe>> {
        return remoteRecipeDataSource.getRecipeByCategory(categoryId)
    }

    override fun getRecipeByQuery(query: String): Single<List<Recipe>> {
        return remoteRecipeDataSource.getRecipeByQuery(query)
    }
}
