package com.ysdc.comet.di.module

import com.ysdc.comet.network.config.NetworkConfig
import com.ysdc.comet.network.config.AppNetworkConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkConfig(): com.ysdc.comet.network.config.NetworkConfig {
        return AppNetworkConfig()
    }
}
