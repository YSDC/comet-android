package com.ysdc.comet.common.di.module

import com.ysdc.comet.common.utils.CrashlyticsUtils
import com.ysdc.comet.common.utils.FormatterUtils
import com.ysdc.comet.common.utils.NetworkUtils
import com.ysdc.comet.common.utils.ValidationUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class SharedUtilsModule {

    @Provides
    @Singleton
    fun provideCrashlyticsUtils(): CrashlyticsUtils {
        return CrashlyticsUtils()
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }

    @Provides
    @Singleton
    fun provideValidationUtils() : ValidationUtils{
        return ValidationUtils()
    }

    @Provides
    @Singleton
    fun provideFormatterUtils() : FormatterUtils {
        return FormatterUtils()
    }
}
