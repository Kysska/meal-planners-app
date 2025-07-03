package com.example.product.local.source

import com.example.product.domain.Product
import com.example.product.domain.ProductInCart
import com.example.product.local.dao.ProductDao
import com.example.product.local.mapper.ProductDatabaseMapper
import com.example.product.local.mapper.ProductInCartDatabaseMapper
import com.example.product.repository.source.LocalProductDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.Date
import timber.log.Timber

class LocalProductDataSourceImpl(
    private val productDao: ProductDao,
    private val databaseMapper: ProductDatabaseMapper,
    private val databaseCartMapper: ProductInCartDatabaseMapper
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

    override fun getProductsInShopCart(date: Date): Observable<List<ProductInCart>> {
        return productDao.getProductsInShopCart(date)
            .map { products ->
                products.map { databaseCartMapper.reverseMap(it.date, databaseMapper.reverseMap(it.product)) }
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun selectedProductInShopCart(product: ProductInCart): Completable {
        val productInCartDb = databaseCartMapper.map(product)
        return productDao.selectedProductInShopCart(productInCartDb)
            .doOnComplete {
                Timber.tag(TAG).d("Update completed successfully")
            }
            .doOnError { throwable ->
                Timber.tag(TAG).e(throwable)
            }
    }

    override fun addProductInShopCart(product: ProductInCart): Completable {
        val productDb = databaseCartMapper.map(product)
        return productDao.addProduct(databaseMapper.map(product.product))
            .concatWith(productDao.addProductInShopCart(productDb))
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
