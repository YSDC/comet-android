package com.ysdc.comet.network

import android.app.Application
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.utils.NetworkUtils
import com.ysdc.comet.network.config.NetworkConfig

/**
 * Basic implementation of the network service
 */

class DefaultNetworkServiceCreator(
    networkConfig: NetworkConfig,
    generalConfig: GeneralConfig,
    application: Application,
    networkUtils: NetworkUtils
) : NetworkServiceCreator(networkConfig, generalConfig, application, networkUtils) {

    override fun clear() {
        clearRetrofit()
    }
}
