package com.ysdc.comet.network.config


import com.ysdc.comet.common.network.config.NetworkConfig

private const val REPLACEMENT_PATTERN = "%s"

/**
 * Implementation of the networkConfig interface
 * @see NetworkConfig
 */
class AppNetworkConfig() : NetworkConfig {

    override fun baseUrl(): String {
        TODO("not implemented")
    }

    override fun authUsername(): String {
        TODO("not implemented")
    }

    override fun authPassword(): String {
        TODO("not implemented")
    }
}
