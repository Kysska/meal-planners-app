package com.example.ui.di

import com.example.recipe.domain.RecipeRepository
import com.example.ui.viewModel.RecipeViewModel
import dagger.Module
import dagger.Provides

@Module
class CommonModule {
    @Provides
    @FeatureScope
    fun provideHomeViewModel(recipeRepository: RecipeRepository): RecipeViewModel {
        return RecipeViewModel(recipeRepository)
    }
}
