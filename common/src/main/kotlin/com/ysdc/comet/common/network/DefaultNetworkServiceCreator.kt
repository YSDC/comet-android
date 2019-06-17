package com.ysdc.comet.common.network

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.network.config.NetworkConstants.HEADER_LANGUAGE
import com.ysdc.comet.common.network.interceptor.LanguageHeaderInterceptor
import com.ysdc.comet.common.network.service.ConfigurationService
import android.app.Application

/**
 * Basic implementation of the network service
 */

class DefaultNetworkServiceCreator(
    networkConfig: NetworkConfig,
    generalConfig: GeneralConfig,
    application: Application,
    crashlyticsUtils: CrashlyticsUtils,
    networkUtils: NetworkUtils
) : NetworkServiceCreator(networkConfig, generalConfig, application, crashlyticsUtils, networkUtils) {
    private var configurationService: ConfigurationService? = null

    init {
        addInterceptor(LanguageHeaderInterceptor(HEADER_LANGUAGE, generalConfig))
    }

    fun getConfigurationService(): ConfigurationService {
        if (configurationService == null) {
            configurationService = buildRetrofit().create(ConfigurationService::class.java)
        }
        return configurationService!!
    }

    override fun clear(){
        configurationService = null
        clearRetrofit()
    }
}
