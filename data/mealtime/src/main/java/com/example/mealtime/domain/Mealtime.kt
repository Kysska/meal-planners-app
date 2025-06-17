package com.example.mealtime.domain

import com.example.recipe.domain.Recipe
import java.util.Date
import java.util.UUID

data class Mealtime(
    val id: Int = UUID.randomUUID().hashCode(),
    val recipe: Recipe = Recipe(),
    val quantity: Int = 0,
    val gram: Int = 0,
    val date: Date = Date(),
    val type: MealtimeType = MealtimeType.BREAKFAST
)

enum class MealtimeType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACKS
}
