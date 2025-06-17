package com.example.product.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ProductRepository {
    fun getProducts(): Single<List<Product>>
    fun getProductById(id: Int): Single<Product>
    fun getProductsByCategory(categoryId: Int): Single<List<Product>>
    fun getProductsByQuery(query: String): Single<List<Product>>
    fun getProductsInShopList(): Observable<List<Product>>
    fun getProductsInShopCart(): Observable<List<Product>>
    fun moveProductInShopList(product: Product): Completable
    fun addProductInShopCart(product: Product): Completable
    fun addProduct(product: Product): Completable
}
