package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.category.local.dao.CategoryDao
import com.example.category.local.dto.CategoryDbEntity
import com.example.mealtime.local.dao.MealtimeDao
import com.example.mealtime.local.dto.MealtimeDbEntity
import com.example.product.local.dao.ProductDao
import com.example.product.local.dto.ProductDbEntity
import com.example.product.local.dto.ProductInCartDbEntity
import com.example.recipe.local.dao.RecipeDao
import com.example.recipe.local.dto.RecipeDbEntity
import com.example.recipe.local.dto.RecipeProductCrossRef

@Database(
    version = 1,
    entities = [CategoryDbEntity::class,
    ProductInCartDbEntity::class,
    ProductDbEntity::class,
    RecipeDbEntity::class, RecipeProductCrossRef::class,
    MealtimeDbEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun recipeDao(): RecipeDao
    abstract fun mealtimeDao(): MealtimeDao

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
