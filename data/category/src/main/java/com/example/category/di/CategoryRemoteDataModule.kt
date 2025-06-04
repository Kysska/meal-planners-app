package com.example.category.di

import com.example.category.network.CategoryApiInterface
import com.example.category.network.RemoteCategoryDataSourceImpl
import com.example.category.repository.source.RemoteCategoryDataSource
import com.example.core.client.ApiClient
import com.example.core.mapper.CategoryNetworkMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CategoryRemoteDataModule {
    @Provides
    @Singleton
    fun provideCategoryRemoteDataSource(): RemoteCategoryDataSource {
        return RemoteCategoryDataSourceImpl(
            ApiClient.create(CategoryApiInterface::class.java),
            CategoryNetworkMapper
        )
    }
}
