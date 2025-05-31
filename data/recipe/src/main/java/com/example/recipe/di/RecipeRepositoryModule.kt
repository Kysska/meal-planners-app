package com.example.recipe.di

import com.example.recipe.domain.RecipeRepository
import com.example.recipe.repository.RecipeRepositoryImpl
import com.example.recipe.repository.source.RemoteRecipeDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RecipeRepositoryModule {
    @Provides
    @Singleton
    fun provideRecipeRepository(
        remoteRecipeDataSource: RemoteRecipeDataSource
    ) : RecipeRepository {
        return RecipeRepositoryImpl(
            remoteRecipeDataSource
        )
    }
}