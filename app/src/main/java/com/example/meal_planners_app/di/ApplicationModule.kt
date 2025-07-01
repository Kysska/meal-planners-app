package com.example.meal_planners_app.di

import com.example.category.di.CategoryLocalDataModule
import com.example.category.di.CategoryRemoteDataModule
import com.example.category.di.CategoryRepositoryModule
import com.example.database.di.DatabaseModule
import com.example.library.di.LibraryComponent
import com.example.mealtime.di.MealtimeLocalDataModule
import com.example.mealtime.di.MealtimeRepositoryModule
import com.example.product.di.ProductLocalDataModule
import com.example.product.di.ProductRemoteDataModule
import com.example.product.di.ProductRepositoryModule
import com.example.recipe.di.RecipeLocalDataModule
import com.example.recipe.di.RecipeRemoteDataModule
import com.example.recipe.di.RecipeRepositoryModule
import com.example.search.di.SearchComponent
import com.example.shoppinglist.di.ShoppingListComponent
import com.example.ui.di.CommonComponent
import com.example.weekplan.di.WeekplanComponent
import dagger.Module

@Module(
    subcomponents = [
        LibraryComponent::class,
        WeekplanComponent::class,
        CommonComponent::class,
        SearchComponent::class,
        ShoppingListComponent::class
    ],
    includes = [
        RecipeRemoteDataModule::class,
        RecipeRepositoryModule::class,
        RecipeLocalDataModule::class,
        ProductRemoteDataModule::class,
        ProductRepositoryModule::class,
        ProductLocalDataModule::class,
        CategoryRepositoryModule::class,
        CategoryRemoteDataModule::class,
        CategoryLocalDataModule::class,
        MealtimeLocalDataModule::class,
        MealtimeRepositoryModule::class,
        DatabaseModule::class
    ]
)
class ApplicationModule
