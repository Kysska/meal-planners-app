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
import io.reactivex.Single
import java.util.Date

@Dao
interface MealtimeDao {
    @Transaction
    @Query("SELECT * FROM mealtime WHERE date = :date")
    fun getAllMealtimeByDate(date: Date): Observable<List<MealtimeWithRecipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun insertMealtime(mealtime: MealtimeDbEntity): Completable

    @Update
    @Transaction
    fun updateMealtime(mealtime: MealtimeDbEntity): Completable

    @Delete
    fun deleteMealtime(mealtime: MealtimeDbEntity): Completable

    @Query("""
        SELECT IFNULL(SUM(r.nutrients_kcal * m.quantity), 0)
        FROM mealtime m
        JOIN recipes r ON m.recipe_id = r.id
        WHERE DATE(m.date) = DATE(:date)
    """)
    fun getDailyKcalSummary(date: Date): Single<Int>

    @Query("""
        SELECT IFNULL(SUM(r.nutrients_protein * m.quantity), 0)
        FROM mealtime m
        JOIN recipes r ON m.recipe_id = r.id
        WHERE DATE(m.date) = DATE(:date)
    """)
    fun getDailyProteinSummary(date: Date): Single<Float>

    @Query("""
        SELECT IFNULL(SUM(r.nutrients_fats * m.quantity), 0)
        FROM mealtime m
        JOIN recipes r ON m.recipe_id = r.id
        WHERE DATE(m.date) = DATE(:date)
    """)
    fun getDailyFatsSummary(date: Date): Single<Float>

    @Query("""
        SELECT IFNULL(SUM(r.nutrients_carbs * m.quantity), 0)
        FROM mealtime m
        JOIN recipes r ON m.recipe_id = r.id
        WHERE DATE(m.date) = DATE(:date)
    """)
    fun getDailyCarbsSummary(date: Date): Single<Float>
}
