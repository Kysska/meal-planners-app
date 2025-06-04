package com.example.meal_planners_app.di

import com.example.library.di.LibraryComponent
import com.example.library.di.LibraryComponentProvider

interface SubComponents : LibraryComponentProvider {
    override fun provideHomeComponent(): LibraryComponent {
        return DiProvider.appComponent().libraryComponent.create()
    }
}
