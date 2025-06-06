package com.example.meal_planners_app.di

import com.example.category.di.CategoryLocalDataModule
import com.example.category.di.CategoryRemoteDataModule
import com.example.category.di.CategoryRepositoryModule
import com.example.database.di.DatabaseModule
import com.example.library.di.LibraryComponent
import com.example.product.di.ProductRemoteDataModule
import com.example.product.di.ProductRepositoryModule
import com.example.recipe.di.RecipeRemoteDataModule
import com.example.recipe.di.RecipeRepositoryModule
import dagger.Module

@Module(
    subcomponents = [LibraryComponent::class],
    includes = [
        RecipeRemoteDataModule::class,
        RecipeRepositoryModule::class,
        ProductRemoteDataModule::class,
        ProductRepositoryModule::class,
        CategoryRepositoryModule::class,
        CategoryRemoteDataModule::class,
        CategoryLocalDataModule::class,
        DatabaseModule::class
    ]
)
class ApplicationModule
