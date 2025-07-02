package com.example.mealtime.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.Date

interface MealtimeRepository {
    fun getAllMealtimeByDate(date: Date): Observable<List<Mealtime>>
    fun insertMealtime(mealtime: Mealtime): Completable
    fun updateMealtime(mealtime: Mealtime): Completable
    fun deleteMealtime(mealtime: Mealtime): Completable
    fun getDailyKcalSummary(date: Date): Single<Int>
    fun getDailyProteinSummary(date: Date): Single<Float>
    fun getDailyFatsSummary(date: Date): Single<Float>
    fun getDailyCarbsSummary(date: Date): Single<Float>
}
