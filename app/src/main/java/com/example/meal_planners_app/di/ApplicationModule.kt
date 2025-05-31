package com.example.meal_planners_app.di

import com.example.recipe.di.RecipeRemoteDataModule
import com.example.recipe.di.RecipeRepositoryModule
import dagger.Module

@Module(
    includes = [
        RecipeRemoteDataModule::class,
        RecipeRepositoryModule::class, ]
)
class ApplicationModule