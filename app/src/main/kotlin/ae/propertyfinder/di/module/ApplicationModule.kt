package ae.propertyfinder.di.module

import ae.propertyfinder.analytics.AnalyticsManager
import ae.propertyfinder.application.AppConfig
import ae.propertyfinder.application.PropertyFinderApplication
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.data.repository.CountryRepository
import com.ysdc.comet.common.di.annotation.ApplicationContext
import com.ysdc.comet.common.navigation.NavigationManager
import ae.propertyfinder.navigation.NavigationManagerImpl
import ae.propertyfinder.ui.UiManager
import android.app.Application
import android.content.Context
import com.facebook.login.LoginManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    fun provideApplication(application: PropertyFinderApplication): Application {
        return application
    }

    @Provides
    @ApplicationContext
    fun provideContext(application: PropertyFinderApplication): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideGeneralConfig(application: Application): GeneralConfig {
        return AppConfig(application)
    }

    @Provides
    fun provideUiManager(countryRepository: CountryRepository): UiManager {
        return UiManager(countryRepository)
    }

    @Provides
    @Singleton
    fun provideLoginManager(): LoginManager {
        return LoginManager.getInstance()
    }

    @Provides
    @Singleton
    fun provideNavigationManager(applicationPreferences: PropertyFinderPreferences, analyticsManager: AnalyticsManager): NavigationManager {
        return NavigationManagerImpl(applicationPreferences, analyticsManager)
    }
}
