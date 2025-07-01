package com.example.product.repository.source

import com.example.product.domain.Product
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.Date

interface LocalProductDataSource {
    fun getProducts(): Single<List<Product>>
    fun getProductById(id: Int): Single<Product>
    fun getProductsByCategory(category: Int): Single<List<Product>>
    fun getProductsByQuery(query: String): Single<List<Product>>
    fun getProductsInShopCart(date: Date): Observable<List<Product>>
    fun selectedProductInShopCart(product: Product): Completable
    fun addProductInShopCart(product: Product): Completable
    fun addProduct(product: Product): Completable
}
