package com.example.recipe.local.mapper

import com.example.recipe.domain.Nutrients
import com.example.recipe.local.dto.NutrientsDbEntity
import com.example.utils.mapper.DatabaseMapper

object NutrientsDatabaseMapper : DatabaseMapper<Nutrients, NutrientsDbEntity> {
    override fun map(from: Nutrients): NutrientsDbEntity {
        return NutrientsDbEntity(
            kcal = from.kcal,
            fats = from.fats,
            proteins = from.protein,
            carbs = from.carbs
        )
    }

    override fun reverseMap(to: NutrientsDbEntity): Nutrients {
        return Nutrients(
            id = to.id,
            kcal = to.kcal,
            protein = to.proteins,
            fats = to.fats,
            carbs = to.carbs
        )
    }
}
