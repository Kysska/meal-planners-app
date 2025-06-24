package com.example.recipe.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.product.local.dto.ProductDbEntity

@Entity(
    primaryKeys = ["recipe_id", "product_id"],
    foreignKeys = [
        ForeignKey(entity = RecipeDbEntity::class, parentColumns = ["id"], childColumns = ["recipe_id"]),
        ForeignKey(entity = ProductDbEntity::class, parentColumns = ["id"], childColumns = ["product_id"])
    ],
    indices = [Index("product_id")]
)
data class RecipeProductCrossRef(
    @ColumnInfo("recipe_id")
    val recipeId: Int,
    @ColumnInfo("product_id")
    val productId: Int
)
