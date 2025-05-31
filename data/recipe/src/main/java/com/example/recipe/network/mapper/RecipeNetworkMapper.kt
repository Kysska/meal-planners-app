package com.example.recipe.network.mapper

import com.example.core.mapper.CategoryNetworkMapper
import com.example.core.mapper.NetworkMapper
import com.example.core.model.Category
import com.example.recipe.domain.Nutrients
import com.example.recipe.domain.Recipe
import com.example.recipe.network.dto.RecipeResponse

internal object RecipeNetworkMapper : NetworkMapper<Recipe, RecipeResponse> {
    override fun map(from: RecipeResponse): Recipe {
        return Recipe(
            id = from.id ?: 0,
            name = from.name ?: "",
            description = from.description ?: "",
            steps = from.steps ?: "",
            times = from.times ?: "",
            difficult = from.difficult ?: "",
            image = from.image ?: "",
            nutrients = from.nutrients?.let { NutritionNetworkMapper.map(it) } ?: Nutrients(),
            category = from.category?.let { CategoryNetworkMapper.map(it) } ?: Category()
        )
    }
}