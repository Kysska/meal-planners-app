package com.example.category.di

import com.example.category.network.CategoryApiInterface
import com.example.category.network.RemoteCategoryDataSourceImpl
import com.example.category.network.mapper.CategoryNetworkMapper
import com.example.category.repository.source.RemoteCategoryDataSource
import com.example.remote.client.ApiClient
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
