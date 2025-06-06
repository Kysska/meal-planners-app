package com.example.category.local.mapper

import com.example.category.domain.Category
import com.example.category.local.dto.CategoryDbEntity
import com.example.utils.mapper.DatabaseMapper

object CategoryDatabaseMapper : DatabaseMapper<CategoryDbEntity, Category> {
    override fun map(from: CategoryDbEntity): Category {
        return Category(
            id = from.id,
            name = from.title
        )
    }

    override fun reverseMap(to: Category): CategoryDbEntity {
        return CategoryDbEntity(
            id = to.id,
            title = to.name
        )
    }
}
