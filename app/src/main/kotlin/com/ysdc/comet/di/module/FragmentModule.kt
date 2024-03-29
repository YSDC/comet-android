package com.ysdc.comet.di.module

import com.ysdc.comet.authentication.ValidateMvpPresenter
import com.ysdc.comet.authentication.ValidateMvpView
import com.ysdc.comet.authentication.ValidatePresenter
import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.authentication.ui.register.RegisterMvpPresenter
import com.ysdc.comet.authentication.ui.register.RegisterMvpView
import com.ysdc.comet.authentication.ui.register.RegisterPresenter
import com.ysdc.comet.authentication.ui.team.TeamMvpPresenter
import com.ysdc.comet.authentication.ui.team.TeamMvpView
import com.ysdc.comet.authentication.ui.team.TeamPresenter
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.di.annotation.FragmentScope
import com.ysdc.comet.common.utils.FormatterUtils
import com.ysdc.comet.common.utils.ValidationUtils
import com.ysdc.comet.repositories.ConfigurationRepository
import com.ysdc.comet.repositories.TeamRepository
import com.ysdc.comet.repositories.UserRepository
import com.ysdc.comet.ui.event.EventMvpPresenter
import com.ysdc.comet.ui.event.EventMvpView
import com.ysdc.comet.ui.event.EventPresenter
import com.ysdc.comet.ui.profile.ProfileMvpPresenter
import com.ysdc.comet.ui.profile.ProfileMvpView
import com.ysdc.comet.ui.profile.ProfilePresenter
import com.ysdc.comet.ui.ranking.RankingMvpPresenter
import com.ysdc.comet.ui.ranking.RankingMvpView
import com.ysdc.comet.ui.ranking.RankingPresenter
import com.ysdc.comet.ui.settings.SettingsMvpPresenter
import com.ysdc.comet.ui.settings.SettingsMvpView
import com.ysdc.comet.ui.settings.SettingsPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    @FragmentScope
    fun provideTeamPresenter(
        errorHandler: ErrorHandler, userRepository: UserRepository, validationUtils: ValidationUtils,
        teamRepository: TeamRepository, configurationRepository: ConfigurationRepository, generalConfig: GeneralConfig
    ): TeamMvpPresenter<TeamMvpView> {
        return TeamPresenter(errorHandler, userRepository, validationUtils, teamRepository, configurationRepository, generalConfig)
    }

    @Provides
    @FragmentScope
    fun provideRegisterPresenter(
        errorHandler: ErrorHandler,
        preferences: MyPreferences,
        validationUtils: ValidationUtils,
        phoneAuthenticationManager: PhoneAuthenticationManager,
        formatterUtils: FormatterUtils,
        userRepository: UserRepository
    ): RegisterMvpPresenter<RegisterMvpView> {
        return RegisterPresenter(errorHandler, preferences, validationUtils, phoneAuthenticationManager, formatterUtils, userRepository)
    }

    @Provides
    @FragmentScope
    fun provideValidatePresenter(
        errorHandler: ErrorHandler,
        preferences: MyPreferences,
        phoneAuthenticationManager: PhoneAuthenticationManager
    ): ValidateMvpPresenter<ValidateMvpView> {
        return ValidatePresenter(errorHandler, preferences, phoneAuthenticationManager)
    }

    @Provides
    @FragmentScope
    fun provideEventPresenter(errorHandler: ErrorHandler): EventMvpPresenter<EventMvpView> {
        return EventPresenter(errorHandler)
    }

    @Provides
    @FragmentScope
    fun provideRankingPresenter(errorHandler: ErrorHandler): RankingMvpPresenter<RankingMvpView> {
        return RankingPresenter(errorHandler)
    }

    @Provides
    @FragmentScope
    fun provideProfilePresenter(errorHandler: ErrorHandler): ProfileMvpPresenter<ProfileMvpView> {
        return ProfilePresenter(errorHandler)
    }

    @Provides
    @FragmentScope
    fun provideSettingsPresenter(errorHandler: ErrorHandler): SettingsMvpPresenter<SettingsMvpView> {
        return SettingsPresenter(errorHandler)
    }
}
