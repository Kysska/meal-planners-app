package com.example.recipe.network.dto

import com.example.category.network.dto.CategoryResponse
import com.example.product.network.dto.ProductResponse
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("recipe_id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("recipe_name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("steps")
    val steps: String?,
    @SerializedName("times")
    val times: String?,
    @SerializedName("difficult")
    val difficult: String?,
    @SerializedName("category")
    val category: CategoryResponse?,
    @SerializedName("nutrients")
    val nutrients: NutrientsResponse?,
    @SerializedName("products")
    val products: List<ProductResponse>? = listOf()
)

data class NutrientsResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("kcal")
    val kcal: Int?,
    @SerializedName("saturates")
    val fats: Float?,
    @SerializedName("carbs")
    val carbs: Float?,
    @SerializedName("sugars")
    val proteins: Float?
)
