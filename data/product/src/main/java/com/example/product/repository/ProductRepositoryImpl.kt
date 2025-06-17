package com.example.product.repository

import com.example.product.domain.Product
import com.example.product.domain.ProductRepository
import com.example.product.repository.source.LocalProductDataSource
import com.example.product.repository.source.RemoteProductDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

internal class ProductRepositoryImpl(
    private val remoteProductDataSource: RemoteProductDataSource,
    private val localProductDataSource: LocalProductDataSource
) : ProductRepository {
    override fun getProducts(): Single<List<Product>> {
        return localProductDataSource.getProducts()
            .flatMap { networkData ->
                Completable.merge(
                    networkData.map { product ->
                        addProduct(product)
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
                    }
                ).andThen(Single.just(networkData))
            }
            .onErrorResumeNext { throwable ->
                Timber.tag(PRODUCT_REPOSITORY).e(throwable, "Error fetching from network, loading from local")
                localProductDataSource.getProductsByQuery(query)
            }
    }

    override fun getProductsInShopList(): Observable<List<Product>> {
        return localProductDataSource.getProductsInShopList()
    }

    override fun getProductsInShopCart(): Observable<List<Product>> {
        return localProductDataSource.getProductsInShopCart()
    }

    override fun moveProductInShopList(product: Product): Completable {
        return localProductDataSource.moveProductInShopList(product)
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
