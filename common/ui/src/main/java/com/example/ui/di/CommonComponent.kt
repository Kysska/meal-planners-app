package com.example.ui.di

import com.example.ui.screens.RecipeDetailsFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope

@FeatureScope
@Subcomponent(modules = [CommonModule::class])
interface CommonComponent {
    fun inject(homeFragment: RecipeDetailsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CommonComponent
    }
}
