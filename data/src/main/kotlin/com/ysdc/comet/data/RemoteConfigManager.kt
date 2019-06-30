package com.ysdc.comet.data

import com.github.ajalt.timberkt.Timber
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants.RECOMMENDED_VERSIONS_SEEN
import com.ysdc.comet.data.model.AppUpdateStatus
import com.ysdc.comet.data.utils.DataConstants.REMOTE_MINIMUM_VERSION
import com.ysdc.comet.data.utils.DataConstants.REMOTE_RECOMMENDED_VERSION
import com.ysdc.comet.model.Version
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*

class RemoteConfigManager(
    private val remoteConfig: FirebaseRemoteConfig,
    private val generalConfig: GeneralConfig,
    private val preferences: MyPreferences
) {
    private val appUpdateStatus: BehaviorSubject<AppUpdateStatus> = BehaviorSubject.create()

    fun initialize() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                Timber.d { "Remote Config: fetchAndActivate completed" }
                if (task.isSuccessful) {

                    if (shouldDisplayRequiredUpdate()) {
                        appUpdateStatus.onNext(AppUpdateStatus.REQUIRED)
                    } else if (shouldDisplayRecommendedUpdate() && !shouldDisplayRequiredUpdate()) {
                        appUpdateStatus.onNext(AppUpdateStatus.RECOMMENDED)

                    } else {
                        appUpdateStatus.onNext(AppUpdateStatus.UP_TO_DATE)
                    }

                }
            }
            .addOnFailureListener {
                Timber.d { "Remote Config: fetchAndActivate failed" }

            }
            .addOnCanceledListener {
                Timber.d { "Remote Config: fetchAndActivate canceled" }
            }
    }

    fun getAppUpdateStatus(): Observable<AppUpdateStatus> {
        return appUpdateStatus
    }

    fun getString(key: String): String {
        return remoteConfig.getString(key)
    }

    /**
     * Utility method that checks shared pref for the flag "recommended version dialog shown"
     *
     * @return boolean if shown
     */
    fun shouldDisplayRecommendedUpdate(): Boolean {
        val remoteRecommendedVersion = remoteConfig.getString(REMOTE_RECOMMENDED_VERSION)
        val alreadyDisplayed = preferences.getAsSet(RECOMMENDED_VERSIONS_SEEN)?.contains(remoteRecommendedVersion) ?: false
        return -1 == generalConfig.currentAppVersion().compareTo(Version(remoteRecommendedVersion)) && !alreadyDisplayed
    }

    /**
     * Utility method that checks shared pref for the flag "required version dialog shown"
     *
     * @return boolean if shown
     */
    private fun shouldDisplayRequiredUpdate(): Boolean {
        val remoteRequiredVersion = remoteConfig.getString(REMOTE_MINIMUM_VERSION)
        return -1 == generalConfig.currentAppVersion().compareTo(Version(remoteRequiredVersion))
    }
}