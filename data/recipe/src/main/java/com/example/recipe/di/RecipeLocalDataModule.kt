package com.example.recipe.di

import com.example.product.repository.source.LocalProductDataSource
import com.example.recipe.local.dao.RecipeDao
import com.example.recipe.local.mapper.RecipeDatabaseMapper
import com.example.recipe.local.source.LocalRecipeDataSourceImpl
import com.example.recipe.repository.source.LocalRecipeDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RecipeLocalDataModule {
    @Provides
    @Singleton
    fun provideRecipeRemoteDataSource(recipeDao: RecipeDao, productDataSource: LocalProductDataSource): LocalRecipeDataSource {
        return LocalRecipeDataSourceImpl(
            recipeDao,
            productDataSource,
            RecipeDatabaseMapper
        )
    }
}
