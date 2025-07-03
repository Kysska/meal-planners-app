package com.example.product.di

import com.example.product.local.dao.ProductDao
import com.example.product.local.mapper.ProductDatabaseMapper
import com.example.product.local.mapper.ProductInCartDatabaseMapper
import com.example.product.local.source.LocalProductDataSourceImpl
import com.example.product.repository.source.LocalProductDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProductLocalDataModule {
    @Provides
    @Singleton
    fun provideProductLocalDataSource(productDao: ProductDao): LocalProductDataSource {
        return LocalProductDataSourceImpl(
            productDao,
            ProductDatabaseMapper,
            ProductInCartDatabaseMapper
        )
    }
}
