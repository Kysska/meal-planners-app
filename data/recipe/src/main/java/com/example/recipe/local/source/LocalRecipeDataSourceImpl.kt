package com.example.recipe.local.source

import com.example.product.local.dao.ProductDao
import com.example.product.local.mapper.ProductDatabaseMapper
import com.example.product.local.source.LocalProductDataSourceImpl
import com.example.product.repository.source.LocalProductDataSource
import com.example.recipe.domain.Recipe
import com.example.recipe.local.dao.RecipeDao
import com.example.recipe.local.dto.RecipeProductCrossRef
import com.example.recipe.local.dto.RecipesWithProducts
import com.example.recipe.local.mapper.RecipeDatabaseMapper
import com.example.recipe.repository.source.LocalRecipeDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

class LocalRecipeDataSourceImpl(
    private val recipeDao: RecipeDao,
    private val productDataSource: LocalProductDataSource,
    private val databaseMapper: RecipeDatabaseMapper
) : LocalRecipeDataSource {
    override fun getRecipes(limit: Int?): Single<List<Recipe>> {
        return recipeDao.getRecipes(limit)
            .map { recipes ->
                recipes.map { recipe ->
                    databaseMapper.reverseMap(recipe.recipe, recipe.products)
                }
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getRecipeById(id: Int): Single<Recipe> {
        return recipeDao.getRecipe(id)
            .map { recipe ->
                databaseMapper.reverseMap(recipe.recipe, recipe.products)
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getRecipesByCategory(category: Int): Single<List<Recipe>> {
        return recipeDao.getRecipeByCategory(category)
            .map { recipes ->
                recipes.map { recipe ->
                    databaseMapper.reverseMap(recipe.recipe, recipe.products)
                }
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getRecipesByQuery(query: String): Single<List<Recipe>> {
        return recipeDao.getRecipeByQuery(query)
            .map { recipes ->
                recipes.map { recipe ->
                    databaseMapper.reverseMap(recipe.recipe, recipe.products)
                }
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun addRecipe(recipe: Recipe): Completable {
        val recipeDb = databaseMapper.map(recipe)
        return recipeDao.insertRecipe(recipeDb)
            .andThen(
                Completable.merge (
                    recipe.products.map { product ->
                        productDataSource.addProduct(product)
                    }
                )
            )
            .andThen(
                Completable.merge (
                    recipe.products.map { product ->
                        val crossRef = RecipeProductCrossRef(
                            recipeId = recipeDb.id,
                            productId = product.id
                        )
                        recipeDao.insertRecipeProductCrossRef(crossRef)
                    }
                )
            )
            .doOnComplete {
                Timber.tag(TAG).d("Insert completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    companion object {
        private const val TAG = "LocalRecipes"
    }
}