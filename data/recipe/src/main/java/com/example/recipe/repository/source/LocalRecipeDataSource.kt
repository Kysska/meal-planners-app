package com.example.recipe.repository.source

import com.example.recipe.domain.Recipe
import io.reactivex.Completable
import io.reactivex.Single

interface LocalRecipeDataSource {
    fun getRecipes(limit: Int?): Single<List<Recipe>>
    fun getRecipeById(id: Int): Single<Recipe>
    fun getRecipesByCategory(category: Int): Single<List<Recipe>>
    fun getRecipesByQuery(query: String): Single<List<Recipe>>
    fun addRecipe(recipe: Recipe): Completable
}