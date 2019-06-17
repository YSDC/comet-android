package ae.propertyfinder.di.module

import ae.propertyfinder.application.PropertyFinderApplication
import com.ysdc.comet.common.data.repository.CountryRepository
import com.ysdc.comet.common.network.config.NetworkConfig
import com.ysdc.comet.common.network.mapper.SearchSettingsMapper
import ae.propertyfinder.network.config.AppNetworkConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkConfig(countryRepository: CountryRepository): NetworkConfig {
        return AppNetworkConfig(countryRepository)
    }

    @Provides
    @Singleton
    fun provideSearchSettingsMapper(context: PropertyFinderApplication): SearchSettingsMapper {
        return SearchSettingsMapper(context)
    }
}
