package com.example.mealtime.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.recipe.local.dto.RecipeDbEntity
import java.util.Date

@Entity(
    tableName = "mealtime",
    foreignKeys = [
        ForeignKey(
            entity = RecipeDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipe_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("recipe_id")]
)
data class MealtimeDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "recipe_id")
    val recipeId: Int,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "gram")
    val gram: Int,
    @ColumnInfo(name = "date")
    val date: Date?,
    @ColumnInfo(name = "type")
    val type: String
)
