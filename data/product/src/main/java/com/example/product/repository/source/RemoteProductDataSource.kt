package com.example.product.repository.source

import com.example.product.domain.Product
import io.reactivex.Single

interface RemoteProductDataSource {
    fun getProduct(): Single<List<Product>>
    fun getProductById(id: Int): Single<Product>
    fun getProductByCategory(categoryId: Int): Single<List<Product>>
}