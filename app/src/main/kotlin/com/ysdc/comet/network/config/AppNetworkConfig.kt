package com.ysdc.comet.network.config

import com.ysdc.comet.BuildConfig
import com.ysdc.comet.common.application.GeneralConfig


private const val REPLACEMENT_PATTERN = "%s"

/**
 * Implementation of the networkConfig interface
 * @see NetworkConfig
 */
class AppNetworkConfig : NetworkConfig {

    override fun baseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun secret(): String{
        return BuildConfig.API_SECRET
    }

    override fun apiKey(): String{
        return BuildConfig.API_SECRET
    }
}
