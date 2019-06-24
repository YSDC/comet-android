package com.ysdc.comet.di.module

import com.ysdc.comet.authentication.ui.activity.AuthenticationActivity
import com.ysdc.comet.common.di.annotation.ActivityScope
import com.ysdc.comet.ui.main.MainActivity
import com.ysdc.comet.ui.splashscreen.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindings {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun provideSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun provideAuthenticationActivity(): AuthenticationActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun provideMainActivity(): MainActivity

}
