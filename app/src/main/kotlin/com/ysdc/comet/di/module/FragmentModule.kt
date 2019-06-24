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
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.di.annotation.FragmentScope
import com.ysdc.comet.common.utils.ProfileValidationUtils
import com.ysdc.comet.data.DataManager
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    @FragmentScope
    fun provideTeamPresenter(errorHandler: ErrorHandler, preferences: MyPreferences, dataManager: DataManager): TeamMvpPresenter<TeamMvpView> {
        return TeamPresenter(errorHandler, preferences, dataManager)
    }

    @Provides
    @FragmentScope
    fun provideRegisterPresenter(
        errorHandler: ErrorHandler,
        preferences: MyPreferences,
        validationUtils: ProfileValidationUtils,
        phoneAuthenticationManager: PhoneAuthenticationManager
    ): RegisterMvpPresenter<RegisterMvpView> {
        return RegisterPresenter(errorHandler, preferences, validationUtils, phoneAuthenticationManager)
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
}
