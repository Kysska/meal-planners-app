package com.example.recipe.repository.source

import com.example.recipe.domain.Recipe
import io.reactivex.Single

interface RemoteRecipeDataSource {
    fun getRecipes(limit: Int?): Single<List<Recipe>>
    fun getRecipeById(id: Int): Single<Recipe>
    fun getRecipeByCategory(categoryId: Int): Single<List<Recipe>>
    fun getRecipeByQuery(query: String): Single<List<Recipe>>
}
