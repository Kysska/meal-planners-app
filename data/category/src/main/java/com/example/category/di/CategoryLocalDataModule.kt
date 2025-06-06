package com.example.category.di

import com.example.category.local.dao.CategoryDao
import com.example.category.local.mapper.CategoryDatabaseMapper
import com.example.category.local.source.LocalCategoryDataSourceImpl
import com.example.category.repository.source.LocalCategoryDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CategoryLocalDataModule {
    @Provides
    @Singleton
    fun provideCategoryLocalDataSource(categoryDao: CategoryDao): LocalCategoryDataSource {
        return LocalCategoryDataSourceImpl(
            categoryDao,
            CategoryDatabaseMapper
        )
    }
}
