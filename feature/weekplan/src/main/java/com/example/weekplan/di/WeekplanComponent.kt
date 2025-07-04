package com.example.weekplan.di

import com.example.weekplan.MealtimeCreateFragment
import com.example.weekplan.PlannerFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope

@FeatureScope
@Subcomponent(modules = [WeekplanModule::class])
interface WeekplanComponent {
    fun inject(homeFragment: PlannerFragment)
    fun inject(addMealtimeFragment: MealtimeCreateFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): WeekplanComponent
    }
}
