package com.example.category.domain

import com.example.core.model.Category
import io.reactivex.Single

interface CategoryRepository {
    fun getCategories() : Single<List<Category>>
}