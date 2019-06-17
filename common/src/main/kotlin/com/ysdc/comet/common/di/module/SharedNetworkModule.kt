package com.ysdc.comet.common.di.module

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.network.DefaultNetworkServiceCreator
import com.ysdc.comet.common.network.config.NetworkConfig
import com.ysdc.comet.common.utils.CrashlyticsUtils
import com.ysdc.comet.common.utils.NetworkUtils
import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by david on 2/8/18.
 */

@Module
open class SharedNetworkModule{

    @Provides
    @Singleton
    fun provideDefaultNetworkServiceCreator(
        networkConfig: NetworkConfig,
        generalConfig: GeneralConfig,
        application: Application,
        crashlyticsUtils: CrashlyticsUtils,
        networkUtils: NetworkUtils
    ): DefaultNetworkServiceCreator {
        return DefaultNetworkServiceCreator(networkConfig, generalConfig, application, crashlyticsUtils, networkUtils)
    }
}
