package ae.propertyfinder.di.module

import ae.propertyfinder.account.ui.login.LoginMvpPresenter
import ae.propertyfinder.account.ui.login.LoginMvpView
import ae.propertyfinder.account.ui.login.LoginPresenter
import ae.propertyfinder.account.ui.reset.ResetMvpPresenter
import ae.propertyfinder.account.ui.reset.ResetMvpView
import ae.propertyfinder.account.ui.reset.ResetPresenter
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.data.repository.ConfigurationRepository
import com.ysdc.comet.common.data.repository.UserRepository
import com.ysdc.comet.common.di.annotation.ActivityScope
import ae.propertyfinder.search.data.repository.SearchRepository
import ae.propertyfinder.ui.main.MainMvpPresenter
import ae.propertyfinder.ui.main.MainMvpView
import ae.propertyfinder.ui.main.MainPresenter
import ae.propertyfinder.ui.splash.SplashMvpPresenter
import ae.propertyfinder.ui.splash.SplashMvpView
import ae.propertyfinder.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides


@Module
class ActivityModule {

    @Provides
    @ActivityScope
    fun provideMainPresenter(errorHandler: ErrorHandler, propertyFinderPreferences: PropertyFinderPreferences): MainMvpPresenter<MainMvpView> {
        return MainPresenter(errorHandler, propertyFinderPreferences)
    }

    @Provides
    @ActivityScope
    fun provideSplashPresenter(
        errorHandler: ErrorHandler,
        propertyFinderPreferences: PropertyFinderPreferences,
        configurationRepository: ConfigurationRepository,
        searchRepository: SearchRepository
    ): SplashMvpPresenter<SplashMvpView> {
        return SplashPresenter(errorHandler, propertyFinderPreferences, configurationRepository, searchRepository)
    }

    @Provides
    @ActivityScope
    fun provideLoginPresenter(errorHandler: ErrorHandler, userRepository: UserRepository): LoginMvpPresenter<LoginMvpView> {
        return LoginPresenter(errorHandler, userRepository)
    }

    @Provides
    @ActivityScope
    fun provideResetPresenter(errorHandler: ErrorHandler, userRepository: UserRepository): ResetMvpPresenter<ResetMvpView> {
        return ResetPresenter(errorHandler, userRepository)
    }
}