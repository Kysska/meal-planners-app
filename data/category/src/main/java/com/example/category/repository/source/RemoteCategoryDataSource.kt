package com.example.category.repository.source

import com.example.core.model.Category
import io.reactivex.Single

interface RemoteCategoryDataSource {
    fun getCategories(): Single<List<Category>>
}
