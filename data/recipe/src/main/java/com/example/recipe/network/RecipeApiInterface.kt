package com.example.recipe.network

import com.example.recipe.network.dto.RecipeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RecipeApiInterface {
    @GET("/recipes")
    fun getRecipes(@Query("limit") limit: Int? = null): Single<List<RecipeResponse>>

    @GET("/recipe/")
    fun getRecipe(@Query("id") id: Int): Single<RecipeResponse>

    @GET("/recipes/category")
    fun getRecipesByCategory(@Query("category") categoryId: Int): Single<List<RecipeResponse>>

    @GET("/recipes/search")
    fun getRecipesByQuery(@Query("search") query: String): Single<List<RecipeResponse>>
}
