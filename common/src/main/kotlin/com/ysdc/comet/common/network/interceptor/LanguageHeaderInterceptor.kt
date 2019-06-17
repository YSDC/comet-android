package com.ysdc.comet.common.network.interceptor

import com.ysdc.comet.common.application.GeneralConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Add the language entry to the header
 */
class LanguageHeaderInterceptor(private val languageParameterName: String, private val generalConfig: GeneralConfig) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestBuilder = original.newBuilder()
            .header(languageParameterName, generalConfig.localeString())

        return chain.proceed(requestBuilder.build())
    }
}
