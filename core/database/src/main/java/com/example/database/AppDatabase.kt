package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.category.local.dao.CategoryDao
import com.example.category.local.dto.CategoryDbEntity

@Database(
    version = 1,
    entities = [CategoryDbEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {
        private const val DATABASE_NAME = "MealAppDatabase"
        private lateinit var INSTANCE: AppDatabase

        @Synchronized
        operator fun invoke(context: Context): AppDatabase {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
            }
            return INSTANCE
        }
    }
}
