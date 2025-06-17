package com.example.product.network.mapper

import com.example.category.domain.Category
import com.example.category.network.mapper.CategoryNetworkMapper
import com.example.product.domain.Product
import com.example.product.network.dto.ProductResponse
import java.util.UUID

object ProductNetworkMapper :
    com.example.utils.mapper.NetworkMapper<Product, ProductResponse> {
    override fun map(from: ProductResponse): Product {
        return Product(
            id = from.id ?: UUID.randomUUID().hashCode(),
            name = from.name ?: "",
            image = from.image ?: "",
            category = from.category?.let { category -> CategoryNetworkMapper.map(category) } ?: Category()
        )
    }
}
