package com.example.product.local.mapper

import com.example.product.domain.Product
import com.example.product.domain.ProductInCart
import com.example.product.local.dto.ProductInCartDbEntity
import com.example.utils.mapper.DatabaseMapper

object ProductInCartDatabaseMapper : DatabaseMapper<ProductInCart, ProductInCartDbEntity> {
    override fun map(from: ProductInCart): ProductInCartDbEntity {
        return ProductInCartDbEntity(
            id = from.id,
            product = from.product.id,
            date = from.date,
            selected = from.selected,
            description = from.description,
            quantity = from.quantity
        )
    }

    fun reverseMap(to: ProductInCartDbEntity, product: Product): ProductInCart {
        return ProductInCart(
            id = to.id,
            date = to.date,
            selected = to.selected,
            description = to.description,
            product = product,
            quantity = to.quantity
        )
    }

    override fun reverseMap(to: ProductInCartDbEntity): ProductInCart {
        return ProductInCart(
            id = to.id,
            date = to.date,
            selected = to.selected,
            description = to.description,
            quantity = to.quantity
        )
    }
}