package com.example.weekplan.di

import com.example.mealtime.domain.MealtimeRepository
import com.example.recipe.domain.RecipeRepository
import com.example.weekplan.MealtimeCreateViewModel
import com.example.weekplan.WeekplanViewModel
import dagger.Module
import dagger.Provides

@Module
class WeekplanModule {
    @Provides
    @FeatureScope
    fun provideHomeViewModel(mealtimeRepository: MealtimeRepository): WeekplanViewModel {
        return WeekplanViewModel(mealtimeRepository)
    }

    @Provides
    @FeatureScope
    fun provideMealtimeCreateViewModel(mealtimeRepository: MealtimeRepository, recipeRepository: RecipeRepository): MealtimeCreateViewModel {
        return MealtimeCreateViewModel(mealtimeRepository, recipeRepository)
    }
}
