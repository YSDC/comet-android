package com.ysdc.comet.network

import android.app.Application
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.utils.NetworkUtils
import com.ysdc.comet.network.config.NetworkConfig
import com.ysdc.comet.network.service.SwissFloorballService

/**
 * Basic implementation of the network service
 */

class DefaultNetworkServiceCreator(
    networkConfig: NetworkConfig,
    generalConfig: GeneralConfig,
    application: Application,
    networkUtils: NetworkUtils
) : NetworkServiceCreator(networkConfig, generalConfig, application, networkUtils) {
    private var swissFloorballService: SwissFloorballService? = null

    override fun clear() {
        clearRetrofit()
    }

    fun getSwissFloorballService(): SwissFloorballService {
        if (swissFloorballService == null) {
            swissFloorballService = buildRetrofit().create(SwissFloorballService::class.java)
        }
        return swissFloorballService!!
    }
}
