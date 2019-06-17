package com.ysdc.comet.common.network

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.network.config.NetworkConfig
import com.ysdc.comet.common.network.config.NetworkConstants.TIMEOUT_IN_SECONDS
import com.ysdc.comet.common.network.interceptor.AuthorizationInterceptor
import com.ysdc.comet.common.network.interceptor.ConnectivityInterceptor
import com.ysdc.comet.common.network.interceptor.NetworkInterceptor
import com.ysdc.comet.common.network.interceptor.UserAgentInterceptor
import com.ysdc.comet.common.utils.CrashlyticsUtils
import com.ysdc.comet.common.utils.NetworkUtils
import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    crashlyticsUtils: CrashlyticsUtils,
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

        val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
        if (generalConfig.isDebug()) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addNetworkInterceptor(StethoInterceptor())
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        this.httpClient.addInterceptor(NetworkInterceptor(crashlyticsUtils))
        this.httpClient.addInterceptor(httpLoggingInterceptor)
        this.httpClient.addInterceptor(AuthorizationInterceptor(networkConfig))
        this.httpClient.addInterceptor(UserAgentInterceptor(application))
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
        }else{
            Timber.d("buildRetrofit: exist")
        }
        return retrofit!!
    }

    protected fun clearRetrofit() {
        retrofit = null
    }

    abstract fun clear()
}
