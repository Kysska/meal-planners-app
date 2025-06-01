package com.example.product.repository

import com.example.product.domain.Product
import com.example.product.domain.ProductRepository
import com.example.product.repository.source.RemoteProductDataSource
import io.reactivex.Single

internal class ProductRepositoryImpl(
    private val remoteProductDataSource: RemoteProductDataSource
) : ProductRepository {
    override fun getProducts(): Single<List<Product>> {
        return remoteProductDataSource.getProduct()
    }

    override fun getProductById(id: Int): Single<Product> {
        return remoteProductDataSource.getProductById(id)
    }

    override fun getProductsByCategory(categoryId: Int): Single<List<Product>> {
        return remoteProductDataSource.getProductByCategory(categoryId)
    }
}