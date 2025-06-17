package com.example.ui.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date?.format(pattern: String = "dd MMMM yyyy", locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(pattern, locale)
    return this?.let { formatter.format(it) } ?: ""
}
