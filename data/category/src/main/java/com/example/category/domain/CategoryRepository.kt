package com.example.category.domain

import io.reactivex.Single

interface CategoryRepository {
    fun getCategories(): Single<List<Category>>
}
