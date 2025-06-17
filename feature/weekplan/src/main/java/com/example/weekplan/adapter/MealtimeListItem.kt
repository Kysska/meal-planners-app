package com.example.weekplan.adapter

import com.example.mealtime.domain.Mealtime
import com.example.mealtime.domain.MealtimeType

sealed class MealtimeListItem {
    data class Header(val type: MealtimeType) : MealtimeListItem()
    data class MealtimeItem(val mealtime: Mealtime) : MealtimeListItem()
}
