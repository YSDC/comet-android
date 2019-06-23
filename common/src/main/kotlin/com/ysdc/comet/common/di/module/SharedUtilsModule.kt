package com.ysdc.comet.common.di.module

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.network.config.NetworkConfig
import com.ysdc.comet.common.utils.CrashlyticsUtils
import com.ysdc.comet.common.utils.NetworkUtils
import com.ysdc.comet.common.utils.ProfileValidationUtils
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
    fun provideProfileValidationUtils() : ProfileValidationUtils{
        return ProfileValidationUtils()
    }
}
