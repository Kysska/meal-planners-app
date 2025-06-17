package com.example.recipe.local.dto

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.product.local.dto.ProductDbEntity

data class RecipesWithProducts(
    @Embedded
    val recipe: RecipeDbEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = RecipeProductCrossRef::class,
            parentColumn = "recipe_id",
            entityColumn = "product_id"
        )
    )
    val products: List<ProductDbEntity>
)
