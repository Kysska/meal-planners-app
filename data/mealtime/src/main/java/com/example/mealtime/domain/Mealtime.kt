package com.example.mealtime.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.recipe.domain.Recipe
import java.time.LocalDate
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
data class Mealtime (
    val id: Int = UUID.randomUUID().hashCode(),
    val recipe: Recipe = Recipe(),
    val quantity: Int = 0,
    val gram: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val type: MealtimeType = MealtimeType.BREAKFAST
)

enum class MealtimeType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACKS
}