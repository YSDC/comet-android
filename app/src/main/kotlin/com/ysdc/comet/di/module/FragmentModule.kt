package com.ysdc.comet.di.module

import dagger.Module

@Module
class FragmentModule {

    /*

    @Provides
    @FragmentScope
    fun provideSavePresenter(errorHandler: ErrorHandler): SaveMvpPresenter<SaveMvpView> {
        return SavePresenter(errorHandler)
    }

    @Provides
    @FragmentScope
    fun provideAccountPresenter(
        errorHandler: ErrorHandler, analyticsManager: AnalyticsManager, userRepository: UserRepository,
        notificationRepository: NotificationRepository, countryRepository: CountryRepository, configurationRepository: ConfigurationRepository
    ): AccountMvpPresenter<AccountMvpView> {
        return AccountPresenter(errorHandler, analyticsManager, userRepository, notificationRepository, countryRepository, configurationRepository)
    }
     */
}
