package com.example.product.domain

import io.reactivex.Single

interface ProductRepository {
    fun getProducts(): Single<List<Product>>
    fun getProductById(id: Int): Single<Product>
    fun getProductsByCategory(categoryId: Int): Single<List<Product>>
}