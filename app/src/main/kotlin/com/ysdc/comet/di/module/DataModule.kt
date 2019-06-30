package com.ysdc.comet.di.module

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.ysdc.comet.BuildConfig
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.data.DataManager
import com.ysdc.comet.data.FirestoreDataManager
import com.ysdc.comet.data.RemoteConfigManager
import com.ysdc.comet.data.utils.DataConstants.REMOTE_CURRENT_SEASON
import com.ysdc.comet.data.utils.DataConstants.REMOTE_MINIMUM_VERSION
import com.ysdc.comet.data.utils.DataConstants.REMOTE_RECOMMENDED_VERSION
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {

    @Singleton
    @Provides
    fun provideFirestore(generalConfig: GeneralConfig): FirebaseFirestore {
        FirebaseFirestore.setLoggingEnabled(generalConfig.isDebug())
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideDataManager(firebaseFirestore: FirebaseFirestore, generalConfig: GeneralConfig): DataManager {
        return FirestoreDataManager(firebaseFirestore, generalConfig)
    }

    @Singleton
    @Provides
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(if (BuildConfig.DEBUG) 0 else 4200)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaults(getRemoteConfigDefault())
        return remoteConfig
    }

    @Singleton
    @Provides
    fun provideRemoteConfigManager(remoteConfig: FirebaseRemoteConfig, generalConfig: GeneralConfig, preferences: MyPreferences): RemoteConfigManager {
        return RemoteConfigManager(remoteConfig, generalConfig, preferences)

    }

    private fun getRemoteConfigDefault(): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        map[REMOTE_RECOMMENDED_VERSION] = "0.0.0"
        map[REMOTE_MINIMUM_VERSION] = "0.0.0"
        map[REMOTE_CURRENT_SEASON] = 2019
        return map
    }
}
