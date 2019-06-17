package com.ysdc.comet.common.di.module

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.di.annotation.ApplicationContext
import com.ysdc.comet.common.utils.CrashlyticsUtils
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedApplicationModule {

    @Provides
    @Singleton
    fun provideMyPreferences(
        @ApplicationContext context: Context,
        crashlyticsUtils: CrashlyticsUtils,
        generalConfig: GeneralConfig
    ): MyPreferences {
        return MyPreferences(context, crashlyticsUtils, generalConfig.getPreferencesFileName())
    }

    @Provides
    @Singleton
    fun provideErrorHandler(@ApplicationContext context: Context): ErrorHandler {
        return ErrorHandler(context)
    }
}
