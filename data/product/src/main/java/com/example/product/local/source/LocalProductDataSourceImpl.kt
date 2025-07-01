package com.example.product.local.source

import com.example.product.domain.Product
import com.example.product.local.dao.ProductDao
import com.example.product.local.dto.ProductInCartDbEntity
import com.example.product.local.mapper.ProductDatabaseMapper
import com.example.product.repository.source.LocalProductDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.Date
import timber.log.Timber

class LocalProductDataSourceImpl(
    private val productDao: ProductDao,
    private val databaseMapper: ProductDatabaseMapper
) : LocalProductDataSource {
    override fun getProducts(): Single<List<Product>> {
        return productDao.getProducts()
            .map { products ->
                databaseMapper.reverseMap(products)
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getProductById(id: Int): Single<Product> {
        return productDao.getProductById(id)
            .map { product ->
                databaseMapper.reverseMap(product)
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getProductsByCategory(category: Int): Single<List<Product>> {
        return productDao.getProductByCategory(category)
            .map { products ->
                databaseMapper.reverseMap(products)
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getProductsByQuery(query: String): Single<List<Product>> {
        return productDao.getProductByQuery(query)
            .map { products ->
                databaseMapper.reverseMap(products)
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun getProductsInShopCart(date: Date): Observable<List<Product>> {
        return productDao.getProductsInShopCart(date)
            .map { products ->
                products.map { databaseMapper.reverseMap(it.product, it.date.date, it.date.selected) }
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun selectedProductInShopCart(product: Product): Completable {
        return productDao.selectedProductInShopCart(
            ProductInCartDbEntity(
                product = product.id,
                date = product.date,
                selected = product.selected
            )
        )
            .doOnComplete {
                Timber.tag(TAG).d("Update completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun addProductInShopCart(product: Product): Completable {
        val productDb = databaseMapper.map(product)
        return productDao.addProduct(productDb)
            .concatWith(productDao.addProductInShopCart(
                ProductInCartDbEntity(
                    product = product.id,
                    date = product.date,
                    selected = product.selected
                )
            ))
            .doOnComplete {
                Timber.tag(TAG).d("Update completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun addProduct(product: Product): Completable {
        val productDb = databaseMapper.map(product)
        return productDao.addProduct(productDb)
            .doOnComplete {
                Timber.tag(TAG).d("Update completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    companion object {
        private const val TAG = "LocalProducts"
    }
}
