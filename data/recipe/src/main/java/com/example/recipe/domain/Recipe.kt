package com.example.recipe.domain

import com.example.category.domain.Category
import com.example.product.domain.Product
import java.util.UUID

data class Recipe(
    val id: Int = UUID.randomUUID().hashCode(),
    val image: String = "",
    val name: String = "",
    val description: String = "",
    val steps: String = "",
    val times: String = "",
    val difficult: String = "",
    val nutrients: Nutrients = Nutrients(),
    val category: Category = Category(),
    val products: List<Product> = listOf()
)

data class Nutrients(
    val id: Int = UUID.randomUUID().hashCode(),
    val kcal: Int = 0,
    val fats: Float = 0f,
    val carbs: Float = 0f,
    val protein: Float = 0f
)
