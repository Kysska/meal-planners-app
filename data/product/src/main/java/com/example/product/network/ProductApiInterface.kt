package com.example.product.network

import com.example.product.network.dto.ProductResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ProductApiInterface {
    @GET("/products")
    fun getProducts() : Single<List<ProductResponse>>

    @GET("/product/")
    fun getProduct(@Query("id") id: Int) : Single<ProductResponse>

    @GET("/products/")
    fun getProductsByCategory(@Query("category") categoryId: Int) : Single<List<ProductResponse>>
}