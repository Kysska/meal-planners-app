package com.example.recipe.network.dto

import com.example.core.model.CategoryResponse
import com.google.gson.annotations.SerializedName

internal data class RecipeResponse(
    @SerializedName("id")
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
    val nutrients: NutrientsResponse?
)

internal data class NutrientsResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("kcal")
    val kcal: Int?,
    @SerializedName("saturates")
    val saturates: Float?,
    @SerializedName("carbs")
    val carbs: Float?,
    @SerializedName("sugars")
    val sugars: Float?
)
