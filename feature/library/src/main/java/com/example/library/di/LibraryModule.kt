package com.example.library.di

import com.example.category.domain.CategoryRepository
import com.example.library.LibraryViewModel
import com.example.recipe.domain.RecipeRepository
import dagger.Module
import dagger.Provides

@Module
class LibraryModule {
    @Provides
    @FeatureScope
    fun provideHomeViewModel(categoryRepository: CategoryRepository, recipeRepository: RecipeRepository): LibraryViewModel {
        return LibraryViewModel(categoryRepository, recipeRepository)
    }
}
