package com.example.ui.di

import com.example.product.domain.ProductRepository
import com.example.recipe.domain.RecipeRepository
import com.example.ui.viewModel.RecipeDetailViewModel
import dagger.Module
import dagger.Provides

@Module
class CommonModule {
    @Provides
    @FeatureScope
    fun provideHomeViewModel(recipeRepository: RecipeRepository, productRepository: ProductRepository): RecipeDetailViewModel {
        return RecipeDetailViewModel(recipeRepository, productRepository)
    }
}
