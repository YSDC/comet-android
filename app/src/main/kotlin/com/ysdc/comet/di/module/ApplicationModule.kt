package com.ysdc.comet.di.module

import android.app.Application
import android.content.Context
import com.ysdc.comet.application.AppConfig
import com.ysdc.comet.application.MyApplication
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.di.annotation.ApplicationContext
import com.ysdc.comet.common.navigation.NavigationManager
import com.ysdc.comet.navigation.NavigationManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    fun provideApplication(application: MyApplication): Application {
        return application
    }

    @Provides
    @ApplicationContext
    fun provideContext(application: MyApplication): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideGeneralConfig(application: Application): GeneralConfig {
        return AppConfig(application)
    }

    @Provides
    @Singleton
    fun provideNavigationManager(): NavigationManager {
        return NavigationManagerImpl()
    }
}
