package com.example.mealtime.repository

import com.example.mealtime.domain.Mealtime
import com.example.mealtime.domain.MealtimeRepository
import com.example.mealtime.repository.source.LocalMealtimeDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.Date

class MealtimeRepositoryImpl(
    private val mealtimeDataSource: LocalMealtimeDataSource
) : MealtimeRepository {
    override fun getAllMealtimeByDate(date: Date): Observable<List<Mealtime>> {
        return mealtimeDataSource.getAllMealtimeByDate(date)
    }

    override fun insertMealtime(mealtime: Mealtime): Completable {
        return mealtimeDataSource.insertMealtime(mealtime)
    }

    override fun updateMealtime(mealtime: Mealtime): Completable {
        return mealtimeDataSource.updateMealtime(mealtime)
    }

    override fun deleteMealtime(mealtime: Mealtime): Completable {
        return mealtimeDataSource.deleteMealtime(mealtime)
    }
}
