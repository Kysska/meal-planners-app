package com.example.product.network.source

import com.example.product.domain.Product
import com.example.product.network.ProductApiInterface
import com.example.product.network.mapper.ProductNetworkMapper
import com.example.product.repository.source.RemoteProductDataSource
import io.reactivex.Single
import timber.log.Timber

internal class RemoteProductDataSourceImpl(
    private val apiInterface: ProductApiInterface,
    private val networkMapper: ProductNetworkMapper
) : RemoteProductDataSource {
    override fun getProduct(): Single<List<Product>> {
        return apiInterface.getProducts()
            .map { response ->
                networkMapper.map(response)
            }
            .doOnError { throwable ->
                Timber.tag("recipe data source").e(throwable)
            }
    }

    override fun getProductById(id: Int): Single<Product> {
        return apiInterface.getProduct(id)
            .map { response ->
                networkMapper.map(response)
            }
            .doOnError { throwable ->
                Timber.tag("recipe data source").e(throwable)
            }
    }

    override fun getProductByCategory(categoryId: Int): Single<List<Product>> {
        return apiInterface.getProductsByCategory(categoryId)
            .map { response ->
                networkMapper.map(response)
            }
            .doOnError { throwable ->
                Timber.tag("recipe data source").e(throwable)
            }
    }
}