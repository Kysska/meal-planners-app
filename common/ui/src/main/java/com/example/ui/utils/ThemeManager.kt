package com.example.ui.utils

import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private var currentMode: Int = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

    fun applyTheme(mode: Int) {
        currentMode = mode
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun toggleTheme() {
        currentMode = when (currentMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
            AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }
        applyTheme(currentMode)
    }
}
