package com.ysdc.comet.di.module

import com.ysdc.comet.authentication.ValidateFragment
import com.ysdc.comet.authentication.ui.register.RegisterFragment
import com.ysdc.comet.authentication.ui.team.TeamFragment
import com.ysdc.comet.common.di.annotation.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindings {
    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideRegisterFragment(): RegisterFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideTeamFragment(): TeamFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideValidateFragment(): ValidateFragment
}
