package com.example.core.mapper

import com.example.core.model.Category
import com.example.core.model.CategoryResponse

object CategoryNetworkMapper : NetworkMapper<Category, CategoryResponse> {
    override fun map(from: CategoryResponse): Category {
        return Category(
            id = from.id ?: 0,
            name = from.name ?: ""
        )
    }
}