package com.example.category.network

import com.example.category.network.dto.CategoryResponse
import io.reactivex.Single
import retrofit2.http.GET

internal interface CategoryApiInterface {
    @GET("/categories")
    fun getCategories(): Single<List<CategoryResponse>>
}
