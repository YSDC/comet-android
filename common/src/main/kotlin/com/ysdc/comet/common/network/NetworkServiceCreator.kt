package com.ysdc.comet.common.network

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.network.config.NetworkConfig
import com.ysdc.comet.common.network.config.NetworkConstants.TIMEOUT_IN_SECONDS
import com.ysdc.comet.common.network.interceptor.ConnectivityInterceptor
import com.ysdc.comet.common.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

/**
 * Create the default network service with the basic interceptors
 */

abstract class NetworkServiceCreator(
    private val networkConfig: NetworkConfig,
    generalConfig: GeneralConfig,
    application: Application,
    networkUtils: NetworkUtils
) {
    private var retrofit: Retrofit? = null
    protected val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    init {
        this.httpClient.connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        this.httpClient.readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)

        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        httpClient.cookieJar(JavaNetCookieJar(cookieManager))

        if (generalConfig.isDebug()) {
            httpClient.addNetworkInterceptor(StethoInterceptor())
        }
        this.httpClient.addInterceptor(ConnectivityInterceptor(application.applicationContext, networkUtils))
    }

    /**
     * Hook to add custom request/response interceptors
     */
    protected fun addInterceptor(interceptor: Interceptor) {
        httpClient.addInterceptor(interceptor)
    }

    protected fun buildRetrofit(): Retrofit {
        if (retrofit == null) {
            Timber.d("buildRetrofit: is null")
            retrofit = Retrofit.Builder()
                .baseUrl(networkConfig.baseUrl())
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        } else {
            Timber.d("buildRetrofit: exist")
        }
        return retrofit!!
    }

    protected fun clearRetrofit() {
        retrofit = null
    }

    abstract fun clear()
}
