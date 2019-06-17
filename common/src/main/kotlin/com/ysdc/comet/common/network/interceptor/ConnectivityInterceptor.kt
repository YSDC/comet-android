package com.ysdc.comet.common.network.interceptor

import com.ysdc.comet.common.exception.NoConnectivityException
import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Check the network connectivity and if not conneted raise a custom exception
 */

class ConnectivityInterceptor(private val mContext: Context, private val networkUtils: com.ysdc.comet.common.utils.NetworkUtils) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkUtils.isNetworkConnected(mContext)) {
            throw NoConnectivityException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}