package com.example.mealtime.local.mapper

import com.example.mealtime.domain.Mealtime
import com.example.mealtime.domain.MealtimeType
import com.example.mealtime.local.dto.MealtimeDbEntity
import com.example.recipe.local.dto.RecipeDbEntity
import com.example.recipe.local.mapper.RecipeDatabaseMapper
import com.example.utils.mapper.DatabaseMapper
import java.util.Date

object MealtimeDatabaseMapper : DatabaseMapper<Mealtime, MealtimeDbEntity> {
    override fun map(from: Mealtime): MealtimeDbEntity {
        return MealtimeDbEntity(
            id = from.id,
            quantity = from.quantity,
            recipeId = from.recipe.id,
            date = from.date,
            gram = from.gram,
            type = from.type.name
        )
    }

    fun reverseMap(to: MealtimeDbEntity, recipe: RecipeDbEntity): Mealtime {
        return Mealtime(
            id = to.id,
            recipe = RecipeDatabaseMapper.reverseMap(recipe),
            quantity = to.quantity,
            gram = to.gram,
            date = to.date ?: Date(),
            type = MealtimeType.valueOf(to.type)
        )
    }

    override fun reverseMap(to: MealtimeDbEntity): Mealtime {
        return Mealtime(
            id = to.id,
            quantity = to.quantity,
            gram = to.gram,
            date = to.date ?: Date(),
            type = MealtimeType.valueOf(to.type)
        )
    }
}
