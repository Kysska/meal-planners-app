package com.example.category.local.mapper

import com.example.category.domain.Category
import com.example.category.local.dto.CategoryDbEntity
import com.example.utils.mapper.DatabaseMapper

object CategoryDatabaseMapper : DatabaseMapper<Category, CategoryDbEntity> {

    override fun map(from: Category): CategoryDbEntity {
        return CategoryDbEntity(
            id = from.id,
            title = from.name
        )
    }

    override fun reverseMap(to: CategoryDbEntity): Category {
        return Category(
            id = to.id,
            name = to.title
        )
    }
}
