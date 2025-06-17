package com.example.recipe.network.mapper

import com.example.category.domain.Category
import com.example.category.network.mapper.CategoryNetworkMapper
import com.example.product.network.mapper.ProductNetworkMapper
import com.example.recipe.domain.Nutrients
import com.example.recipe.domain.Recipe
import com.example.recipe.network.dto.RecipeResponse
import com.example.utils.mapper.NetworkMapper
import java.util.UUID

object RecipeNetworkMapper : NetworkMapper<Recipe, RecipeResponse> {
    override fun map(from: RecipeResponse): Recipe {
        return Recipe(
            id = from.id ?: UUID.randomUUID().hashCode(),
            name = from.name ?: "",
            description = from.description ?: "",
            steps = from.steps ?: "",
            times = from.times ?: "",
            difficult = from.difficult ?: "",
            image = from.image ?: "",
            nutrients = from.nutrients?.let { NutritionNetworkMapper.map(it) } ?: Nutrients(),
            category = from.category?.let { CategoryNetworkMapper.map(it) } ?: Category(),
            products = from.products?.let { ProductNetworkMapper.map(it) } ?: listOf()
        )
    }
}
