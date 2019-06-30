package com.ysdc.comet.di.module

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.MyPreferences
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
    fun provideTeamRepository(defaultNetworkServiceCreator: DefaultNetworkServiceCreator, generalConfig: GeneralConfig): TeamRepository {
        return TeamRepository(defaultNetworkServiceCreator, generalConfig)
    }

    @Provides
    @Singleton
    fun provideConfigurationRepository(remoteConfigManager: RemoteConfigManager, preferences: MyPreferences): ConfigurationRepository {
        return ConfigurationRepository(remoteConfigManager, preferences)
    }

/*
    @Provides
    @Singleton
    fun provideUserRepository(propertyFinderPreferences: PropertyFinderPreferences): UserRepository {
        return UserRepositoryImpl(propertyFinderPreferences)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(
        propertyFinderPreferences: PropertyFinderPreferences,
        generalConfig: GeneralConfig
    ): NotificationRepository {
        return NotificationRepositoryImpl(propertyFinderPreferences, generalConfig)
    }

    @Provides
    @Singleton
    fun provideConfigurationRepository(
        defaultNetworkServiceCreator: DefaultNetworkServiceCreator,
        generalConfig: GeneralConfig,
        propertyFinderPreferences: PropertyFinderPreferences,
        countryRepository: CountryRepository,
        searchSettingsMapper: SearchSettingsMapper
    ): ConfigurationRepository {
        return ConfigurationRepositoryImpl(
            defaultNetworkServiceCreator,
            generalConfig,
            propertyFinderPreferences,
            countryRepository,
            searchSettingsMapper
        )
    }

    @Provides
    @Singleton
    fun providePropertyRepository(
        searchNetworkServiceCreator: SearchNetworkServiceCreator,
        propertyFinderPreferences: PropertyFinderPreferences,
        userRepository: UserRepository,
        generalConfig: GeneralConfig
    ): PropertyRepository {
        return PropertyRepositoryImp(
            searchNetworkServiceCreator,
            propertyFinderPreferences,
            userRepository,
            generalConfig
        )
    }
 */
}