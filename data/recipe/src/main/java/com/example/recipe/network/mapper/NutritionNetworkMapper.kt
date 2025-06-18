package com.example.recipe.network.mapper

import com.example.recipe.domain.Nutrients
import com.example.recipe.network.dto.NutrientsResponse
import com.example.utils.mapper.NetworkMapper

object NutritionNetworkMapper : NetworkMapper<Nutrients, NutrientsResponse> {
    override fun map(from: NutrientsResponse): Nutrients {
        return Nutrients(
            id = from.id ?: 0,
            kcal = from.kcal ?: 0,
            carbs = from.carbs ?: 0f,
            fats = from.fats ?: 0f,
            protein = from.proteins ?: 0f
        )
    }
}
