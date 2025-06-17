package com.example.mealtime.domain

import io.reactivex.Completable
import io.reactivex.Observable
import java.time.LocalDate

interface MealtimeRepository {
    fun getAllMealtimeByDate(date: LocalDate) : Observable<List<Mealtime>>
    fun insertMealtime(mealtime: Mealtime): Completable
    fun updateMealtime(mealtime: Mealtime): Completable
    fun deleteMealtime(mealtime: Mealtime): Completable
}