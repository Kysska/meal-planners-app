package com.example.mealtime.repository.source

import com.example.mealtime.domain.Mealtime
import io.reactivex.Completable
import io.reactivex.Observable
import java.time.LocalDate

interface LocalMealtimeDataSource {
    fun getAllMealtimeByDate(date: LocalDate) : Observable<List<Mealtime>>
    fun insertMealtime(mealtime: Mealtime): Completable
    fun updateMealtime(mealtime: Mealtime): Completable
    fun deleteMealtime(mealtime: Mealtime): Completable
}