package com.ysdc.comet.di.module

import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.authentication.ui.activity.AuthenticationMvpPresenter
import com.ysdc.comet.authentication.ui.activity.AuthenticationMvpView
import com.ysdc.comet.authentication.ui.activity.AuthenticationPresenter
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.di.annotation.ActivityScope
import com.ysdc.comet.repositories.ConfigurationRepository
import com.ysdc.comet.repositories.TeamRepository
import com.ysdc.comet.repositories.UserRepository
import com.ysdc.comet.ui.main.MainMvpPresenter
import com.ysdc.comet.ui.main.MainMvpView
import com.ysdc.comet.ui.main.MainPresenter
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
        userRepository: UserRepository,
        teamRepository: TeamRepository,
        configurationRepository: ConfigurationRepository
    ): AuthenticationMvpPresenter<AuthenticationMvpView> {
        return AuthenticationPresenter(errorHandler, phoneAuthenticationManager, userRepository, teamRepository, configurationRepository)
    }

    @Provides
    @ActivityScope
    fun provideSplashPresenter(
        errorHandler: ErrorHandler, phoneAuthenticationManager: PhoneAuthenticationManager, configurationRepository: ConfigurationRepository
    ): SplashMvpPresenter<SplashMvpView> {
        return SplashPresenter(errorHandler, phoneAuthenticationManager, configurationRepository)
    }

    @Provides
    @ActivityScope
    fun provideMainPresenter(
        errorHandler: ErrorHandler
    ): MainMvpPresenter<MainMvpView> {
        return MainPresenter(errorHandler)
    }
}