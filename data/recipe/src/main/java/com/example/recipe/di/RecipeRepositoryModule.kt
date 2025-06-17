package com.example.recipe.di

import com.example.recipe.domain.RecipeRepository
import com.example.recipe.repository.RecipeRepositoryImpl
import com.example.recipe.repository.source.LocalRecipeDataSource
import com.example.recipe.repository.source.RemoteRecipeDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RecipeRepositoryModule {
    @Provides
    @Singleton
    fun provideRecipeRepository(
        remoteDataSource: RemoteRecipeDataSource,
        localDataSource: LocalRecipeDataSource
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            remoteDataSource,
            localDataSource
        )
    }
}
