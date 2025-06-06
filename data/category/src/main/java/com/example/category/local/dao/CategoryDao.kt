package com.example.category.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.category.local.dto.CategoryDbEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: CategoryDbEntity): Completable

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Single<List<CategoryDbEntity>>
}
