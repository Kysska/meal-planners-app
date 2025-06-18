package com.example.search.di

import com.example.recipe.domain.RecipeRepository
import com.example.search.SearchViewModel
import dagger.Module
import dagger.Provides

@Module
class SearchModule {
    @Provides
    @FeatureScope
    fun provideSearchViewModel(recipeRepository: RecipeRepository): SearchViewModel {
        return SearchViewModel(recipeRepository)
    }
}
