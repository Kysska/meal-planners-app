package com.example.weekplan.di

import com.example.mealtime.domain.MealtimeRepository
import com.example.recipe.domain.RecipeRepository
import com.example.weekplan.WeekplanViewModel
import dagger.Module
import dagger.Provides

@Module
class WeekplanModule {
    @Provides
    @FeatureScope
    fun provideHomeViewModel(mealtimeRepository: MealtimeRepository, recipeRepository: RecipeRepository): WeekplanViewModel {
        return WeekplanViewModel(mealtimeRepository, recipeRepository)
    }
}
