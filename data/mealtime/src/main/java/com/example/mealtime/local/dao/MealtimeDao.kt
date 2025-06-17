package com.example.mealtime.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mealtime.local.dto.MealtimeDbEntity
import com.example.mealtime.local.dto.MealtimeWithRecipe
import io.reactivex.Completable
import io.reactivex.Observable
import java.time.LocalDate

@Dao
interface MealtimeDao {
    @Transaction
    @Query("SELECT * FROM mealtime WHERE date = :date")
    fun getAllMealtimeByDate(date: LocalDate) : Observable<List<MealtimeWithRecipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun insertMealtime(mealtime: MealtimeDbEntity): Completable

    @Update
    @Transaction
    fun updateMealtime(mealtime: MealtimeDbEntity): Completable

    @Delete
    fun deleteMealtime(mealtime: MealtimeDbEntity): Completable
}