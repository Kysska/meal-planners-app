package com.example.category.di

import com.example.category.domain.CategoryRepository
import com.example.category.repository.CategoryRepositoryImpl
import com.example.category.repository.source.RemoteCategoryDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CategoryRepositoryModule {
    @Provides
    @Singleton
    fun provideCategoryRepository(
        remoteCategoryDataSource: RemoteCategoryDataSource
    ) : CategoryRepository {
        return CategoryRepositoryImpl(
            remoteCategoryDataSource
        )
    }
}