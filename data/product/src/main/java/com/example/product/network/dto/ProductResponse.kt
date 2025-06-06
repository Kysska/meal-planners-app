package com.example.product.network.dto

import com.example.category.network.dto.CategoryResponse
import com.google.gson.annotations.SerializedName

internal data class ProductResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("category")
    val category: CategoryResponse?
)
