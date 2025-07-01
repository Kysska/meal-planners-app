package com.example.meal_planners_app.di

import com.example.library.di.LibraryComponent
import com.example.library.di.LibraryComponentProvider
import com.example.search.di.SearchComponent
import com.example.search.di.SearchComponentProvider
import com.example.shoppinglist.di.ShoppingListComponent
import com.example.shoppinglist.di.ShoppingListComponentProvider
import com.example.ui.di.CommonComponent
import com.example.ui.di.CommonComponentProvider
import com.example.weekplan.di.WeekplanComponent
import com.example.weekplan.di.WeekplanComponentProvider

interface SubComponents : LibraryComponentProvider, WeekplanComponentProvider,
    CommonComponentProvider, SearchComponentProvider, ShoppingListComponentProvider {
    override fun provideHomeComponent(): LibraryComponent {
        return DiProvider.appComponent().libraryComponent.create()
    }

    override fun providePlannerComponent(): WeekplanComponent {
        return DiProvider.appComponent().plannerComponent.create()
    }

    override fun provideCommonComponent(): CommonComponent {
        return DiProvider.appComponent().commonComponent.create()
    }

    override fun provideSearchComponent(): SearchComponent {
        return DiProvider.appComponent().searchComponent.create()
    }

    override fun provideShoppingListComponent(): ShoppingListComponent {
        return DiProvider.appComponent().shoppingListComponent.create()
    }
}
