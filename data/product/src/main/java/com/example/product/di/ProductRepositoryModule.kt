package com.example.product.di

import com.example.product.domain.ProductRepository
import com.example.product.repository.ProductRepositoryImpl
import com.example.product.repository.source.LocalProductDataSource
import com.example.product.repository.source.RemoteProductDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProductRepositoryModule {
    @Provides
    @Singleton
    fun provideProductRepository(
        remoteProductDataSource: RemoteProductDataSource,
        localProductDataSource: LocalProductDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(
            remoteProductDataSource,
            localProductDataSource
        )
    }
}
