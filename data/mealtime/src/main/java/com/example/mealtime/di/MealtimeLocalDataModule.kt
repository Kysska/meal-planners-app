package com.example.mealtime.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mealtime.local.dao.MealtimeDao
import com.example.mealtime.local.mapper.MealtimeDatabaseMapper
import com.example.mealtime.local.source.LocalMealtimeDataSourceImpl
import com.example.mealtime.repository.source.LocalMealtimeDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Module
class MealtimeLocalDataModule {

    @Provides
    @Singleton
    fun provideMealtimeLocalDataSource(mealtimeDao: MealtimeDao): LocalMealtimeDataSource {
        return LocalMealtimeDataSourceImpl(
            mealtimeDao,
            MealtimeDatabaseMapper
        )
    }
}
