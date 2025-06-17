package com.example.product.local.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.category.local.dto.CategoryDbEntity

@Entity(tableName = "products")
data class ProductDbEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("image")
    val image: String,
    @Embedded(prefix = "category_")
    val category: CategoryDbEntity
)
