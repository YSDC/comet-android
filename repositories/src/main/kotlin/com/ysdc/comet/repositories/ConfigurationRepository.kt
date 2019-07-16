package com.ysdc.comet.repositories

import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants
import com.ysdc.comet.data.RemoteConfigManager
import com.ysdc.comet.data.model.AppUpdateStatus
import com.ysdc.comet.data.utils.DataConstants
import com.ysdc.comet.data.utils.DataConstants.REMOTE_CURRENT_SEASON
import io.reactivex.Observable
import java.util.*

class ConfigurationRepository(
    private val remoteConfigManager: RemoteConfigManager,
    private val preferences: MyPreferences
) {

    fun loadConfiguration() {
        remoteConfigManager.initialize()
    }

    fun getCurrentSeason() : Int {
        return remoteConfigManager.getInt(REMOTE_CURRENT_SEASON)
    }

    fun appUpdateStatus(): Observable<AppUpdateStatus> {
        return remoteConfigManager.getAppUpdateStatus()
    }

    fun shouldDisplayRecommandedDialog(): Boolean {
        return remoteConfigManager.shouldDisplayRecommendedUpdate()

    }

    fun saveRecommendedUpdateDialogSeen() {
        val versions: MutableSet<String> = preferences.getAsSet(PrefsConstants.RECOMMENDED_VERSIONS_SEEN)?.toMutableSet() ?: TreeSet()
        versions.add(remoteConfigManager.getString(DataConstants.REMOTE_RECOMMENDED_VERSION))
        preferences.setSet(PrefsConstants.RECOMMENDED_VERSIONS_SEEN, versions)
    }
}