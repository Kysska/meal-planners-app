package com.example.product.local.mapper

import com.example.category.local.mapper.CategoryDatabaseMapper
import com.example.product.domain.Product
import com.example.product.local.dto.ProductDbEntity
import com.example.utils.mapper.DatabaseMapper

object ProductDatabaseMapper : DatabaseMapper<Product, ProductDbEntity> {
    override fun map(from: Product): ProductDbEntity {
        return ProductDbEntity(
            id = from.id,
            name = from.name,
            image = from.image,
            category = CategoryDatabaseMapper.map(from.category)
        )
    }

    override fun reverseMap(to: ProductDbEntity): Product {
        return Product(
            id = to.id,
            name = to.name,
            image = to.image,
            category = CategoryDatabaseMapper.reverseMap(to.category)
        )
    }
}
