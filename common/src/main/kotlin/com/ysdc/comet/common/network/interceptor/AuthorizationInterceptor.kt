package com.ysdc.comet.common.network.interceptor

import com.ysdc.comet.common.network.config.NetworkConfig
import com.ysdc.comet.common.network.config.NetworkConstants.HEADER_AUTHORIZATION
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Add the auth header to the query
 */

class AuthorizationInterceptor(private val networkConfig: NetworkConfig) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestBuilder = original.newBuilder()
            .header(HEADER_AUTHORIZATION, Credentials.basic(networkConfig.authUsername(), networkConfig.authPassword()))
            .method(original.method(), original.body())
        return chain.proceed(requestBuilder.build())
    }
}
