package com.example.product.domain

import com.example.core.model.Category
import java.util.UUID

data class Product(
    val id: Int = UUID.randomUUID().hashCode(),
    val name: String = "",
    val image: String = "",
    val category: Category = Category()
)
