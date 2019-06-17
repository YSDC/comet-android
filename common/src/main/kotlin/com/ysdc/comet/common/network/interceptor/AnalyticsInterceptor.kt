package com.ysdc.comet.common.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by david on 2/8/18.
 */

class AnalyticsInterceptor  : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //TODO
        return chain.proceed(request)
    }
}
