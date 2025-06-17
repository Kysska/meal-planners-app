package com.example.mealtime.local.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.example.recipe.local.dto.RecipeDbEntity

data class MealtimeWithRecipe(
    @Embedded
    val mealtime: MealtimeDbEntity,
    @Relation(
        parentColumn = "recipe_id",
        entityColumn = "id"
    )
    val recipe: RecipeDbEntity
)
