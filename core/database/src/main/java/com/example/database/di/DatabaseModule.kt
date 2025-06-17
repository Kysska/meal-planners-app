package com.example.database.di

import android.content.Context
import com.example.category.local.dao.CategoryDao
import com.example.database.AppDatabase
import com.example.mealtime.local.dao.MealtimeDao
import com.example.product.local.dao.ProductDao
import com.example.recipe.local.dao.RecipeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.invoke(context)
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideMealtimeDao(database: AppDatabase): MealtimeDao {
        return database.mealtimeDao()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(database: AppDatabase): RecipeDao {
        return database.recipeDao()
    }
}
