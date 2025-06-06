package com.example.category.repository.source

import com.example.category.domain.Category
import io.reactivex.Completable
import io.reactivex.Single

interface LocalCategoryDataSource {
    fun addCategory(category: Category): Completable
    fun getAllCategories(): Single<List<Category>>
}
