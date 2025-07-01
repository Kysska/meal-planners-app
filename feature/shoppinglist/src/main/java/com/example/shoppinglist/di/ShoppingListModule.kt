package com.example.shoppinglist.di

import com.example.product.domain.ProductRepository
import com.example.shoppinglist.ProductViewModel
import dagger.Module
import dagger.Provides

@Module
class ShoppingListModule {
    @Provides
    @FeatureScope
    fun provideShoppingListViewModel(productRepository: ProductRepository): ProductViewModel {
        return ProductViewModel(productRepository)
    }
}
