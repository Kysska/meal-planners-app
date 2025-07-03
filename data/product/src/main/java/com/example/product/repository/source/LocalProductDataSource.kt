package com.example.product.repository.source

import com.example.product.domain.Product
import com.example.product.domain.ProductInCart
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.Date

interface LocalProductDataSource {
    fun getProducts(): Single<List<Product>>
    fun getProductById(id: Int): Single<Product>
    fun getProductsByCategory(category: Int): Single<List<Product>>
    fun getProductsByQuery(query: String): Single<List<Product>>
    fun getProductsInShopCart(date: Date): Observable<List<ProductInCart>>
    fun selectedProductInShopCart(product: ProductInCart): Completable
    fun addProductInShopCart(product: ProductInCart): Completable
    fun addProduct(product: Product): Completable
}
