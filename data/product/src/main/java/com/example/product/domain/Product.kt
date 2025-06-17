package com.example.product.domain

import com.example.category.domain.Category
import java.time.LocalDate
import java.util.UUID

data class Product(
    val id: Int = UUID.randomUUID().hashCode(),
    val name: String = "",
    val image: String = "",
    val category: Category = Category(),
    val date: LocalDate? = null
)
