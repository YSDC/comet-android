package ae.propertyfinder.network.config


import ae.propertyfinder.BuildConfig
import com.ysdc.comet.common.data.repository.CountryRepository
import com.ysdc.comet.common.network.config.NetworkConfig

private const val REPLACEMENT_PATTERN = "%s"

/**
 * Implementation of the networkConfig interface
 * @see NetworkConfig
 */
class AppNetworkConfig(private val countryRepository: CountryRepository) : NetworkConfig {

    override fun baseUrl(): String {
        return if (BuildConfig.BASE_URL.contains(REPLACEMENT_PATTERN)) {
            String.format(
                BuildConfig.BASE_URL,
                countryRepository.getCurrentCountry()?.code ?: throw IllegalStateException("The base cannot be build cause the current country is not set")
            )
        } else {
            BuildConfig.BASE_URL
        }
    }

    override fun authUsername(): String {
        return BuildConfig.BASE_AUTH_USERNAME
    }

    override fun authPassword(): String {
        return BuildConfig.BASE_AUTH_PASSWORD
    }
}
