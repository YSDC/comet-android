package com.ysdc.comet.di.module

import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.authentication.ui.activity.AuthenticationMvpPresenter
import com.ysdc.comet.authentication.ui.activity.AuthenticationMvpView
import com.ysdc.comet.authentication.ui.activity.AuthenticationPresenter
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.di.annotation.ActivityScope
import com.ysdc.comet.repository.TeamRepository
import com.ysdc.comet.ui.splashscreen.SplashMvpPresenter
import com.ysdc.comet.ui.splashscreen.SplashMvpView
import com.ysdc.comet.ui.splashscreen.SplashPresenter
import dagger.Module
import dagger.Provides


@Module
class ActivityModule {

    @Provides
    @ActivityScope
    fun provideAuthenticationPresenter(
        errorHandler: ErrorHandler,
        phoneAuthenticationManager: PhoneAuthenticationManager,
        preferences: MyPreferences
    ): AuthenticationMvpPresenter<AuthenticationMvpView> {
        return AuthenticationPresenter(errorHandler, phoneAuthenticationManager, preferences)
    }

    @Provides
    @ActivityScope
    fun provideSplashPresenter(
        errorHandler: ErrorHandler, preferences: MyPreferences,
        phoneAuthenticationManager: PhoneAuthenticationManager,
        teamRepository: TeamRepository
    ): SplashMvpPresenter<SplashMvpView> {
        return SplashPresenter(errorHandler, preferences, phoneAuthenticationManager, teamRepository)
    }
}