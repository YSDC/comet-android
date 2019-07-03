package com.ysdc.comet.di.module

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.data.DataManager
import com.ysdc.comet.data.RemoteConfigManager
import com.ysdc.comet.network.DefaultNetworkServiceCreator
import com.ysdc.comet.repositories.ConfigurationRepository
import com.ysdc.comet.repositories.TeamRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by david on 2/8/18.
 */

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTeamRepository(
        defaultNetworkServiceCreator: DefaultNetworkServiceCreator,
        generalConfig: GeneralConfig,
        dataManager: DataManager,
        preferences: MyPreferences
    ): TeamRepository {
        return TeamRepository(defaultNetworkServiceCreator, generalConfig, dataManager, preferences)
    }

    @Provides
    @Singleton
    fun provideConfigurationRepository(remoteConfigManager: RemoteConfigManager, preferences: MyPreferences): ConfigurationRepository {
        return ConfigurationRepository(remoteConfigManager, preferences)
    }
}