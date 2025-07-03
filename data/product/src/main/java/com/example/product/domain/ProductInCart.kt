package com.example.product.domain

import java.util.Date
import java.util.UUID

data class ProductInCart(
    val id: Int = UUID.randomUUID().hashCode(),
    val product: Product = Product(),
    val date: Date = Date(),
    val selected: Boolean = false,
    val description: String = "",
    val quantity: Int = 0
)
