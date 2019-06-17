package ae.propertyfinder.data.repository

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.PrefsConstants.CURRENT_AREA_UNIT
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.data.repository.ConfigurationRepository
import com.ysdc.comet.common.data.repository.CountryRepository
import com.ysdc.comet.common.exception.BackendException
import com.ysdc.comet.common.network.DefaultNetworkServiceCreator
import com.ysdc.comet.common.network.config.NetworkConstants.FILTER_SETTINGS_CATEGORY_KEY
import com.ysdc.comet.common.network.config.NetworkConstants.FILTER_SETTINGS_PRICE_TYPE
import com.ysdc.comet.common.network.config.NetworkConstants.FILTER_SETTINGS_PROPERTY_TYPE_KEY
import com.ysdc.comet.common.network.mapper.SearchSettingsMapper
import com.ysdc.comet.common.network.model.searchsettings.SearchSettingsResponse
import com.ysdc.comet.common.utils.AppConstants
import ae.propertyfinder.model.AreaUnit
import ae.propertyfinder.model.SearchFilters
import ae.propertyfinder.model.SearchSettings
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ConfigurationRepositoryImpl(
    private val serviceCreator: DefaultNetworkServiceCreator,
    private val generalConfig: GeneralConfig,
    private val propertyFinderPreferences: PropertyFinderPreferences,
    private val countryRepository: CountryRepository,
    private val searchSettingsMapper: SearchSettingsMapper

) : ConfigurationRepository {

    private var searchSettings: SearchSettings = SearchSettings.Builder().build()

    override fun areaUnits(): List<AreaUnit> {
        return AreaUnit.values().toList()
    }

    override fun getBackendAreaUnit(): AreaUnit {
        return countryRepository.getCurrentCountry()?.areaUnit ?: AreaUnit.SQM
    }

    override fun useDifferentAreaUnit(): Boolean {
        return getBackendAreaUnit() != getCurrentAreaUnit()
    }

    override fun getCurrentAreaUnit(): AreaUnit {
        return AreaUnit.fromId(propertyFinderPreferences.getAsInt(CURRENT_AREA_UNIT, (countryRepository.getCurrentCountry()?.areaUnit ?: AreaUnit.SQM).id))
    }

    override fun setCurrentAreaUnit(areaUnit: AreaUnit) {
        propertyFinderPreferences.setInt(CURRENT_AREA_UNIT, areaUnit.id)
    }

    override fun getSearchSettings(): SearchSettings {
        return searchSettings
    }

    override fun loadConfiguration(searchFilters: SearchFilters): Completable {
        return Completable.defer {
            retrieveSearchSettings(searchFilters)
                .doOnSuccess { this.searchSettings = it }
                .ignoreElement()
        }
    }

    override fun retrieveSearchSettings(searchFilters: SearchFilters): Single<SearchSettings> {
        return Single.defer {
            serviceCreator.getConfigurationService()
                .getSearchSettings(generalConfig.localeString(), getFilterMap(searchFilters))
                .subscribeOn(Schedulers.io())
                .map { searchSettingsResponse: SearchSettingsResponse ->
                    //TODO: See with backend if this endpoint can be upgraded to the json API stuff
                    if (searchSettingsResponse.hasErrors()) {
                        throw BackendException(searchSettingsResponse.getErrorDetail() ?: AppConstants.EMPTY_STRING)
                    }
                    searchSettingsMapper.map(searchSettingsResponse)
                }
        }
    }


    /**
     * @return a map of the filters values, used for the network call for the filters value
     */
    private fun getFilterMap(searchFilters: SearchFilters): MutableMap<String, String> {
        val parameters: MutableMap<String, String> = HashMap()

        parameters[FILTER_SETTINGS_CATEGORY_KEY] = searchFilters.category.id.toString()
        searchFilters.priceType?.let {
            parameters[FILTER_SETTINGS_PRICE_TYPE] = it
        }
        searchFilters.propertyType?.let { list ->
            if (list.isNotEmpty()) {
                parameters[FILTER_SETTINGS_PROPERTY_TYPE_KEY] =
                    list.filter { it != AppConstants.KEY_FROM_ANY_FILTER.toInt() }.joinToString { it.toString() }
            }
        }
        return parameters
    }
}