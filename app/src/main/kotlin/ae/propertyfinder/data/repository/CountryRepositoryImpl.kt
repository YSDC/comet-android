package ae.propertyfinder.data.repository

import ae.propertyfinder.BuildConfig
import ae.propertyfinder.model.Country
import com.ysdc.comet.common.data.prefs.PrefsConstants.COUNTRY_CODE
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.data.repository.CountryRepository
import java.util.*

/**
 * Countains all method to CRUD current country used on data relative to it
 */

class CountryRepositoryImpl(private val appPrefs: PropertyFinderPreferences) : CountryRepository {

    var country: Country? = null
        private set

    init {
        val c = appPrefs.getAsString(COUNTRY_CODE)
        if (c != null) {
            this.country = Country.fromCode(c)
        }
    }

    /**
     * Return the current country
     */
    override fun getCurrentCountry(): Country? {
        //TODO remove this
        return Country.UAE
        //return country
    }

    /**
     * Update current country
     * @param country the new current country
     */
    override fun updateCurrent(newCountry: Country) {
        this.appPrefs.setString(COUNTRY_CODE, newCountry.code)
        this.country = newCountry
    }

    /**
     * Delete the current country selected
     */
    override fun cleanCountrySelection() {
        this.appPrefs.removeKey(COUNTRY_CODE)
        this.country = null
    }

    /**
     * @return the authentication secret relative to the current country
     */
    override fun authentication(): String? {
        if (country == null) {
            return null
        }
        return BuildConfig.COUNTRIES_AUTHENTICATION[country!!.code]
    }

    /**
     * @return the list of all the country available in the app
     */
    override fun countries(): List<Country> {
        val countries = ArrayList<Country>()
        for (key in BuildConfig.COUNTRIES_AUTHENTICATION.keys) {
            countries.add(Country.fromCode(key))
        }
        return countries.sortedWith(compareBy(Country::code, Country::key))
    }
}
