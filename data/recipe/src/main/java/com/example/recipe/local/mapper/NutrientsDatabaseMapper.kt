package com.example.recipe.local.mapper

import com.example.recipe.domain.Nutrients
import com.example.recipe.local.dto.NutrientsDbEntity
import com.example.utils.mapper.DatabaseMapper

object NutrientsDatabaseMapper : DatabaseMapper<Nutrients, NutrientsDbEntity> {
    override fun map(from: Nutrients): NutrientsDbEntity {
        return NutrientsDbEntity(
            id = from.id,
            kcal = from.kcal,
            saturates = from.saturates,
            sugars = from.sugars,
            carbs = from.carbs
        )
    }

    override fun reverseMap(to: NutrientsDbEntity): Nutrients {
        return Nutrients(
            id = to.id,
            kcal = to.kcal,
            sugars = to.sugars,
            saturates = to.saturates,
            carbs = to.carbs
        )
    }
}