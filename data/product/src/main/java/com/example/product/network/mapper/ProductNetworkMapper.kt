package com.example.product.network.mapper

import com.example.core.mapper.CategoryNetworkMapper
import com.example.core.mapper.NetworkMapper
import com.example.core.model.Category
import com.example.product.domain.Product
import com.example.product.network.dto.ProductResponse
import java.util.UUID

internal object ProductNetworkMapper : NetworkMapper<Product, ProductResponse> {
    override fun map(from: ProductResponse): Product {
        return Product(
            id = from.id ?: UUID.randomUUID().hashCode(),
            name = from.name ?: "",
            image = from.image ?: "",
            category = from.category?.let { CategoryNetworkMapper.map(it) } ?: Category()
        )
    }
}