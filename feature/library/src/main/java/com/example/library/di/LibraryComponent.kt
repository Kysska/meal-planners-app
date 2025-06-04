package com.example.library.di

import com.example.library.LibraryFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope

@FeatureScope
@Subcomponent(modules = [LibraryModule::class])
interface LibraryComponent {
    fun inject(homeFragment: LibraryFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LibraryComponent
    }
}
