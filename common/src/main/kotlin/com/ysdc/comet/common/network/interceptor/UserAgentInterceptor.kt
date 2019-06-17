package com.ysdc.comet.common.network.interceptor

import com.ysdc.comet.common.network.config.NetworkConstants.HEADER_USER_AGENT
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import android.app.Application
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.util.*

/**
 * Add the user agent to the query
 */
class UserAgentInterceptor(private val application: Application) : Interceptor {

    private val userAgent: String
    private val applicationVersionName: String
        get() {
            var applicationVersionName = EMPTY_STRING
            try {
                applicationVersionName = packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                Timber.e(e, "getApplicationVersionName(): %s", e.message)
            }

            return applicationVersionName
        }

    private val packageInfo: PackageInfo
        @Throws(PackageManager.NameNotFoundException::class)
        get() = application.packageManager.getPackageInfo(application.packageName, 0)

    init {
        userAgent = getUserAgent()
    }

    private fun getUserAgent(): String {
        return String.format(
            Locale.getDefault(), "propertyfinder-android/%s %s",
            applicationVersionName,
            System.getProperty("http.agent")
        )
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header(HEADER_USER_AGENT, userAgent)
            .method(original.method(), original.body())
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
