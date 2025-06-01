package com.example.product.di

import com.example.core.client.ApiClient
import com.example.product.network.ProductApiInterface
import com.example.product.network.mapper.ProductNetworkMapper
import com.example.product.network.source.RemoteProductDataSourceImpl
import com.example.product.repository.source.RemoteProductDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProductRemoteDataModule {
    @Provides
    @Singleton
    fun provideProductRemoteDataSource(): RemoteProductDataSource {
        return RemoteProductDataSourceImpl(
            ApiClient.create(ProductApiInterface::class.java),
            ProductNetworkMapper
        )
    }
}