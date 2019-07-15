package com.ysdc.comet.di.module

import com.ysdc.comet.authentication.ValidateFragment
import com.ysdc.comet.authentication.ui.register.RegisterFragment
import com.ysdc.comet.authentication.ui.team.TeamFragment
import com.ysdc.comet.common.di.annotation.FragmentScope
import com.ysdc.comet.ui.event.EventFragment
import com.ysdc.comet.ui.event.EventPresenter
import com.ysdc.comet.ui.profile.ProfileFragment
import com.ysdc.comet.ui.ranking.RankingFragment
import com.ysdc.comet.ui.settings.SettingsFragment
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

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideEventFragment(): EventFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideRankingFragment(): RankingFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideProfileFragment(): ProfileFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideSettingsFragment(): SettingsFragment
}
