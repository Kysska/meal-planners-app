package com.example.category.domain

import java.util.UUID

data class Category(
    val id: Int = UUID.randomUUID().hashCode(),
    val name: String = ""
)
