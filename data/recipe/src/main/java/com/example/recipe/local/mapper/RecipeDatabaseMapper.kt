package com.example.recipe.local.mapper

import com.example.category.local.mapper.CategoryDatabaseMapper
import com.example.product.local.dto.ProductDbEntity
import com.example.product.local.mapper.ProductDatabaseMapper
import com.example.recipe.domain.Recipe
import com.example.recipe.local.dto.RecipeDbEntity
import com.example.utils.mapper.DatabaseMapper

object RecipeDatabaseMapper : DatabaseMapper<Recipe, RecipeDbEntity> {
    override fun map(from: Recipe): RecipeDbEntity {
        return RecipeDbEntity(
            name = from.name,
            image = from.image,
            description = from.description,
            steps = from.steps,
            times = from.times,
            difficult = from.difficult,
            category = CategoryDatabaseMapper.map(from.category),
            nutrients = NutrientsDatabaseMapper.map(from.nutrients)

        )
    }

    override fun reverseMap(to: RecipeDbEntity): Recipe {
        return Recipe(
            id = to.id,
            name = to.name,
            image = to.image,
            description = to.description,
            steps = to.steps,
            times = to.times,
            difficult = to.difficult,
            category = CategoryDatabaseMapper.reverseMap(to.category),
            nutrients = NutrientsDatabaseMapper.reverseMap(to.nutrients)
        )
    }

    fun reverseMap(to: RecipeDbEntity, products: List<ProductDbEntity>): Recipe {
        return Recipe(
            id = to.id,
            name = to.name,
            image = to.image,
            description = to.description,
            steps = to.steps,
            times = to.times,
            difficult = to.difficult,
            category = CategoryDatabaseMapper.reverseMap(to.category),
            nutrients = NutrientsDatabaseMapper.reverseMap(to.nutrients),
            products = ProductDatabaseMapper.reverseMap(products)
        )
    }
}
