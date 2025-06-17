package com.example.recipe.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.recipe.local.dto.RecipeDbEntity
import com.example.recipe.local.dto.RecipeProductCrossRef
import com.example.recipe.local.dto.RecipesWithProducts
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes LIMIT COALESCE(:limit, -1)")
    @Transaction
    fun getRecipes(limit: Int?): Single<List<RecipesWithProducts>>

    @Query("SELECT * FROM recipes WHERE id = :id ")
    @Transaction
    fun getRecipe(id: Int): Single<RecipesWithProducts>

    @Query("SELECT * FROM recipes WHERE category_id = :category ")
    @Transaction
    fun getRecipeByCategory(category: Int): Single<List<RecipesWithProducts>>

    @Query("SELECT * FROM recipes WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' ")
    @Transaction
    fun getRecipeByQuery(query: String): Single<List<RecipesWithProducts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeDbEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeProductCrossRef(crossRef: RecipeProductCrossRef): Completable
}