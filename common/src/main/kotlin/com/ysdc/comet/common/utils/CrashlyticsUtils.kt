package com.ysdc.comet.common.utils

import android.util.Log
import com.crashlytics.android.Crashlytics
import timber.log.Timber

/**
 * Contains all the operation on Crashlytics and the logic to decide if a message should be sent
 * or not to the server.
 */

class CrashlyticsUtils {

    class CrashlyticsTree : Timber.Tree() {

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
                return
            }

            Crashlytics.setInt("priority", priority)
            Crashlytics.setString("tag", tag)
            Crashlytics.setString("message", message)

            if (t == null) {
                Crashlytics.logException(Exception(message))
            } else {
                Crashlytics.logException(t)
            }
        }
    }

    fun logException(throwable: Throwable) {
        Crashlytics.logException(throwable)
    }

    fun log(category: String, action: String, label: String, value: Long = 0) {
        val breadCrumb = String.format("%s|%s|%s|%s", category, action, label, value)
        Crashlytics.log(breadCrumb)
    }

    fun setKeyString(key: String, value: String) {
        Crashlytics.setString(key, value)
    }
}
