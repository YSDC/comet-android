package ae.propertyfinder.di.module

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.data.repository.*
import com.ysdc.comet.common.network.DefaultNetworkServiceCreator
import com.ysdc.comet.common.network.mapper.SearchSettingsMapper
import ae.propertyfinder.data.repository.*
import ae.propertyfinder.search.network.SearchNetworkServiceCreator
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
    fun provideCountryRepository(propertyFinderPreferences: PropertyFinderPreferences): CountryRepository {
        return CountryRepositoryImpl(propertyFinderPreferences)
    }

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
}