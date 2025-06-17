package com.example.mealtime.di

import com.example.mealtime.domain.MealtimeRepository
import com.example.mealtime.repository.MealtimeRepositoryImpl
import com.example.mealtime.repository.source.LocalMealtimeDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MealtimeRepositoryModule {
    @Provides
    @Singleton
    fun provideMealtimeRepository(
        localMealtimeDataSource: LocalMealtimeDataSource
    ): MealtimeRepository {
        return MealtimeRepositoryImpl(
            localMealtimeDataSource
        )
    }
}