package com.example.product.repository

import com.example.product.domain.Product
import com.example.product.domain.ProductRepository
import com.example.product.repository.source.LocalProductDataSource
import com.example.product.repository.source.RemoteProductDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.Date
import timber.log.Timber

internal class ProductRepositoryImpl(
    private val remoteProductDataSource: RemoteProductDataSource,
    private val localProductDataSource: LocalProductDataSource
) : ProductRepository {
    override fun getProducts(): Single<List<Product>> {
        return remoteProductDataSource.getProduct()
            .flatMap { networkData ->
                Completable.merge(
                    networkData.map { product ->
                        Timber.tag(PRODUCT_REPOSITORY).d(product.toString())
                        addProduct(product)
                            .onErrorResumeNext { error ->
                                Timber.e(error, "Failed to save product: $product")
                                Completable.complete()
                            }
                    }
                ).andThen(Single.just(networkData))
            }
            .onErrorResumeNext { throwable ->
                Timber.tag(PRODUCT_REPOSITORY).e(throwable, "Error fetching from network, loading from local")
                localProductDataSource.getProducts()
            }
    }

    override fun getProductById(id: Int): Single<Product> {
        return remoteProductDataSource.getProductById(id)
            .onErrorResumeNext { throwable ->
                Timber.tag(PRODUCT_REPOSITORY).e(throwable, "Error fetching product from network, loading from local")
                localProductDataSource.getProductById(id)
            }
    }

    override fun getProductsByCategory(categoryId: Int): Single<List<Product>> {
        return remoteProductDataSource.getProductByCategory(categoryId)
            .flatMap { networkData ->
                Completable.merge(
                    networkData.map { product ->
                        addProduct(product)
                            .onErrorResumeNext { error ->
                                Timber.e(error, "Failed to save product: $product")
                                Completable.complete()
                            }
                    }
                ).andThen(Single.just(networkData))
            }
            .onErrorResumeNext { throwable ->
                Timber.tag(PRODUCT_REPOSITORY).e(throwable, "Error fetching from network, loading from local")
                localProductDataSource.getProductsByCategory(categoryId)
            }
    }

    override fun getProductsByQuery(query: String): Single<List<Product>> {
        return remoteProductDataSource.getProductsByQuery(query)
            .flatMap { networkData ->
                Completable.merge(
                    networkData.map { product ->
                        addProduct(product)
                            .onErrorResumeNext { error ->
                                Timber.e(error, "Failed to save product: $product")
                                Completable.complete()
                            }
                    }
                ).andThen(Single.just(networkData))
            }
            .onErrorResumeNext { throwable ->
                Timber.tag(PRODUCT_REPOSITORY).e(throwable, "Error fetching from network, loading from local")
                localProductDataSource.getProductsByQuery(query)
            }
    }

    override fun getProductsInShopCart(date: Date): Observable<List<Product>> {
        return localProductDataSource.getProductsInShopCart(date)
    }

    override fun selectedProductInShopCart(product: Product): Completable {
        return localProductDataSource.selectedProductInShopCart(product)
    }

    override fun addProductInShopCart(product: Product): Completable {
        return localProductDataSource.addProductInShopCart(product)
    }

    override fun addProduct(product: Product): Completable {
        return localProductDataSource.addProduct(product)
    }

    companion object {
        private const val PRODUCT_REPOSITORY = "CategoryRepository"
    }
}
