package com.example.search.di

import com.example.search.SearchFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope

@FeatureScope
@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {
    fun inject(homeFragment: SearchFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }
}
