package com.example.weekplan.di

import com.example.mealtime.domain.MealtimeRepository
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
}
