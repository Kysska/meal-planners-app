package com.example.recipe.local.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.category.local.dto.CategoryDbEntity

@Entity(tableName = "recipes")
data class RecipeDbEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("image")
    val image: String,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("steps")
    val steps: String,
    @ColumnInfo("times")
    val times: String,
    @ColumnInfo("difficult")
    val difficult: String,
    @Embedded(prefix = "nutrients_")
    val nutrients: NutrientsDbEntity,
    @Embedded(prefix = "category_")
    val category: CategoryDbEntity
)

data class NutrientsDbEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("kcal")
    val kcal: Int,
    @ColumnInfo("fats")
    val fats: Float,
    @ColumnInfo("carbs")
    val carbs: Float,
    @ColumnInfo("protein")
    val proteins: Float
)
