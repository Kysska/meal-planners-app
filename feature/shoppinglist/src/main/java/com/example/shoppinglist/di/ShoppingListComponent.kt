package com.example.shoppinglist.di

import com.example.shoppinglist.ProductCreateFragment
import com.example.shoppinglist.ProductListFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope

@FeatureScope
@Subcomponent(modules = [ShoppingListModule::class])
interface ShoppingListComponent {
    fun inject(homeFragment: ProductListFragment)
    fun inject(productCreateFragment: ProductCreateFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ShoppingListComponent
    }
}
