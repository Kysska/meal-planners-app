package com.example.weekplan.utils

import com.example.mealtime.domain.MealtimeType

fun String.toMealtimeType(): MealtimeType = when (this) {
    "Завтрак" -> MealtimeType.BREAKFAST
    "Обед" -> MealtimeType.LUNCH
    "Ужин" -> MealtimeType.DINNER
    "Перекусы" -> MealtimeType.SNACKS
    else -> throw IllegalArgumentException("Unknown type")
}
