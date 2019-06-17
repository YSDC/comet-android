package com.ysdc.comet.common.di.module

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.network.config.NetworkConfig
import com.ysdc.comet.common.utils.CrashlyticsUtils
import com.ysdc.comet.common.utils.NetworkUtils
import com.ysdc.comet.common.ui.utils.GlideUtils
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
    fun provideGlideUtils(networkConfig : NetworkConfig, generalConfig: GeneralConfig): GlideUtils {
        return GlideUtils(networkConfig, generalConfig)
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}
