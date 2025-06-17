package com.example.recipe.repository

import com.example.recipe.domain.Recipe
import com.example.recipe.domain.RecipeRepository
import com.example.recipe.repository.source.LocalRecipeDataSource
import com.example.recipe.repository.source.RemoteRecipeDataSource
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber

internal class RecipeRepositoryImpl(
    private val remoteRecipeDataSource: RemoteRecipeDataSource,
    private val localRecipeDataSource: LocalRecipeDataSource
) : RecipeRepository {
    override fun getRecipes(limit: Int?): Single<List<Recipe>> {
        return remoteRecipeDataSource.getRecipes(limit)
            .flatMap { networkData ->
                Completable.merge(
                    networkData.map { recipe ->
                        addRecipe(recipe)
                    }
                ).andThen(Single.just(networkData))
            }
            .onErrorResumeNext { throwable ->
                Timber.tag(RECIPE_REPOSITORY).e(throwable, "Error fetching from network, loading from local")
                localRecipeDataSource.getRecipes(limit)
            }
    }

    override fun getRecipeById(id: Int): Single<Recipe> {
        return remoteRecipeDataSource.getRecipeById(id)
            .onErrorResumeNext { throwable ->
                Timber.tag(RECIPE_REPOSITORY).e(throwable, "Error fetching from network, loading from local")
                localRecipeDataSource.getRecipeById(id)
            }
    }

    override fun getRecipeByCategory(categoryId: Int): Single<List<Recipe>> {
        return remoteRecipeDataSource.getRecipeByCategory(categoryId)
            .flatMap { networkData ->
                Completable.merge(
                    networkData.map { recipe ->
                        addRecipe(recipe)
                    }
                ).andThen(Single.just(networkData))
            }
            .onErrorResumeNext { throwable ->
                Timber.tag(RECIPE_REPOSITORY).e(throwable, "Error fetching from network, loading from local")
                localRecipeDataSource.getRecipesByCategory(categoryId)
            }
    }

    override fun getRecipeByQuery(query: String): Single<List<Recipe>> {
        return remoteRecipeDataSource.getRecipeByQuery(query)
            .flatMap { networkData ->
                Completable.merge(
                    networkData.map { recipe ->
                        addRecipe(recipe)
                    }
                ).andThen(Single.just(networkData))
            }
            .onErrorResumeNext { throwable ->
                Timber.tag(RECIPE_REPOSITORY).e(throwable, "Error fetching from network, loading from local")
                localRecipeDataSource.getRecipesByQuery(query)
            }
    }

    override fun addRecipe(recipe: Recipe): Completable {
        return localRecipeDataSource.addRecipe(recipe)
    }

    companion object {
        private const val RECIPE_REPOSITORY = "CategoryRepository"
    }
}
