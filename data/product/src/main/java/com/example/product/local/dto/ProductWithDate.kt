package com.example.product.local.dto

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithDate(
    @Embedded val date: ProductInCartDbEntity,

    @Relation(
        parentColumn = "product_id",
        entityColumn = "id"
    )
    val product: ProductDbEntity
)
