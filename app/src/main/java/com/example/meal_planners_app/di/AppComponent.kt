package com.example.meal_planners_app.di

import android.content.Context
import com.example.library.di.LibraryComponent
import com.example.meal_planners_app.MainActivity
import com.example.search.di.SearchComponent
import com.example.shoppinglist.di.ShoppingListComponent
import com.example.ui.di.CommonComponent
import com.example.weekplan.di.WeekplanComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ApplicationModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    val libraryComponent: LibraryComponent.Factory
    val plannerComponent: WeekplanComponent.Factory
    val commonComponent: CommonComponent.Factory
    val searchComponent: SearchComponent.Factory
    val shoppingListComponent: ShoppingListComponent.Factory
}
