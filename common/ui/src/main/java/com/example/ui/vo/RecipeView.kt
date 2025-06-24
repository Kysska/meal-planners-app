package com.example.ui.vo

import android.os.Parcelable
import java.util.UUID
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeView(
    val id: Int = UUID.randomUUID().hashCode(),
    val image: String = "",
    val name: String = "",
    val times: String = ""
) : Parcelable
