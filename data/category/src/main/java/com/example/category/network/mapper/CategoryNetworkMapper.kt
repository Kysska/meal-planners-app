package com.example.category.network.mapper

import com.example.category.domain.Category
import com.example.category.network.dto.CategoryResponse
import com.example.utils.mapper.NetworkMapper

object CategoryNetworkMapper : NetworkMapper<Category, CategoryResponse> {
    override fun map(from: CategoryResponse): Category {
        return Category(
            id = from.id ?: 0,
            name = from.name ?: ""
        )
    }
}
