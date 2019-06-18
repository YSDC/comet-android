package com.ysdc.comet.common.di.module

import android.app.Application
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.network.DefaultNetworkServiceCreator
import com.ysdc.comet.common.network.config.NetworkConfig
import com.ysdc.comet.common.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by david on 2/8/18.
 */

@Module
open class SharedNetworkModule {

    @Provides
    @Singleton
    fun provideDefaultNetworkServiceCreator(
        networkConfig: NetworkConfig,
        generalConfig: GeneralConfig,
        application: Application,
        networkUtils: NetworkUtils
    ): DefaultNetworkServiceCreator {
        return DefaultNetworkServiceCreator(networkConfig, generalConfig, application, networkUtils)
    }
}
