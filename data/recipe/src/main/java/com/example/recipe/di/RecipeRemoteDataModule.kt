package com.example.recipe.di

import com.example.recipe.network.RecipeApiInterface
import com.example.recipe.network.mapper.RecipeNetworkMapper
import com.example.recipe.network.source.RemoteRecipeDataSourceImpl
import com.example.recipe.repository.source.RemoteRecipeDataSource
import com.example.remote.client.ApiClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RecipeRemoteDataModule {
    @Provides
    @Singleton
    fun provideRecipeRemoteDataSource(): RemoteRecipeDataSource {
        return RemoteRecipeDataSourceImpl(
            ApiClient.create(RecipeApiInterface::class.java),
            RecipeNetworkMapper
        )
    }
}
