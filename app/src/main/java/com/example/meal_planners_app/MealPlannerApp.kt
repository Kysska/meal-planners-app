package com.example.meal_planners_app

import android.app.Application
import com.example.meal_planners_app.di.DiProvider
import timber.log.Timber

class MealPlannerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        initDebugTools()
        DiProvider.buildDi(this)
    }

    private fun initDebugTools() {
        if(BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var instance: MealPlannerApp? = null
            private set
    }
}