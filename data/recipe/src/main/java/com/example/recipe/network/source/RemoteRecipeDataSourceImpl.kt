package com.example.recipe.network.source

import com.example.recipe.domain.Recipe
import com.example.recipe.network.RecipeApiInterface
import com.example.recipe.network.mapper.RecipeNetworkMapper
import com.example.recipe.repository.source.RemoteRecipeDataSource
import io.reactivex.Single
import timber.log.Timber

internal class RemoteRecipeDataSourceImpl(
    private val apiInterface: RecipeApiInterface,
    private val networkMapper: RecipeNetworkMapper
) : RemoteRecipeDataSource {
    override fun getRecipes(limit: Int?): Single<List<Recipe>> {
        return apiInterface.getRecipes(limit)
            .map { response ->
                networkMapper.map(response)
            }
            .doOnError { throwable ->
                Timber.tag("recipe data source").e(throwable)
            }
    }

    override fun getRecipeById(id: Int): Single<Recipe> {
        return apiInterface.getRecipe(id)
            .map { response ->
                networkMapper.map(response)
            }
            .doOnError { throwable ->
                Timber.tag("recipe data source").e(throwable)
            }
    }

    override fun getRecipeByCategory(categoryId: Int): Single<List<Recipe>> {
        return apiInterface.getRecipesByCategory(categoryId)
            .map { response ->
                networkMapper.map(response)
            }
            .doOnError { throwable ->
                Timber.tag("recipe data source").e(throwable)
            }
    }

    override fun getRecipeByQuery(query: String): Single<List<Recipe>> {
        return apiInterface.getRecipesByQuery(query)
            .map { response ->
                networkMapper.map(response)
            }
            .doOnError { throwable ->
                Timber.tag("recipe data source").e(throwable)
            }
    }
}
