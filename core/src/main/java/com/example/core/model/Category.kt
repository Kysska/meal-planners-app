package com.example.core.model

import java.util.UUID

data class Category(
    val id: Int = UUID.randomUUID().hashCode(),
    val name: String = ""
)
