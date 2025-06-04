package com.example.category.repository

import com.example.category.domain.CategoryRepository
import com.example.category.repository.source.RemoteCategoryDataSource
import com.example.core.model.Category
import io.reactivex.Single

internal class CategoryRepositoryImpl(
    private val remoteCategoryDataSource: RemoteCategoryDataSource
) : CategoryRepository {
    override fun getCategories(): Single<List<Category>> {
        return remoteCategoryDataSource.getCategories()
    }
}
