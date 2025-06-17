package com.example.recipe.domain

import io.reactivex.Completable
import io.reactivex.Single

interface RecipeRepository {
    fun getRecipes(limit: Int?): Single<List<Recipe>>
    fun getRecipeById(id: Int): Single<Recipe>
    fun getRecipeByCategory(categoryId: Int): Single<List<Recipe>>
    fun getRecipeByQuery(query: String): Single<List<Recipe>>
    fun addRecipe(recipe: Recipe): Completable
}
