package com.ysdc.comet.di.module

import android.app.Application
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.utils.NetworkUtils
import com.ysdc.comet.network.config.NetworkConfig
import com.ysdc.comet.network.config.AppNetworkConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkConfig(): NetworkConfig {
        return AppNetworkConfig()
    }

    @Provides
    @Singleton
    fun provideDefaultNetworkServiceCreator(
        networkConfig: NetworkConfig,
        generalConfig: GeneralConfig,
        application: Application,
        networkUtils: NetworkUtils
    ): com.ysdc.comet.network.DefaultNetworkServiceCreator {
        return com.ysdc.comet.network.DefaultNetworkServiceCreator(networkConfig, generalConfig, application, networkUtils)
    }
}
