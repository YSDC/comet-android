package ae.propertyfinder.analytics

import ae.propertyfinder.analytics.dispatcher.FacebookConsumerDispatcher
import ae.propertyfinder.analytics.dispatcher.FirebaseConsumerDispatcher
import ae.propertyfinder.analytics.dispatcher.SnowplowConsumerDispatcher
import ae.propertyfinder.analytics.dispatcher.TagManagerDispatcher
import com.ysdc.comet.common.data.repository.CountryRepository
import com.ysdc.comet.common.data.repository.UserRepository
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import ae.propertyfinder.model.Property
import ae.propertyfinder.model.SearchFilters
import timber.log.Timber


class AnalyticsManagerImpl(
    private val facebookConsumerDispatcher: FacebookConsumerDispatcher,
    private val firebaseConsumerDispatcher: FirebaseConsumerDispatcher,
    private val snowplowConsumerDispatcher: SnowplowConsumerDispatcher,
    private val tagManagerDispatcher: TagManagerDispatcher,
    private val userRepository: UserRepository,
    private val countryRepository: CountryRepository
) : AnalyticsManager {
    override fun trackLoadProperty(screenName: String, property: Property, page: Int?, position: Int?, isListView: Boolean) {
        snowplowConsumerDispatcher.listingLoadEvent(screenName, userRepository.getLoginStatus(),
            userRepository.userDetails()?.userToken ?: EMPTY_STRING, userRepository.userDetails()?.userId ?: EMPTY_STRING,
            property, page, position, isListView)
    }

    override fun trackDisplayedProperty(screenName: String, property: Property, page: Int?, position: Int?, isListView: Boolean) {
        snowplowConsumerDispatcher.listingViewedEvent(screenName, userRepository.getLoginStatus(),
            userRepository.userDetails()?.userToken ?: EMPTY_STRING, userRepository.userDetails()?.userId ?: EMPTY_STRING,
            property, page, position, isListView)
    }

    override fun trackClickedProperty(screenName: String, property: Property, page: Int?, position: Int?, isListView: Boolean) {
        snowplowConsumerDispatcher.propertyClickedEvent(screenName, userRepository.getLoginStatus(),
            userRepository.userDetails()?.userToken ?: EMPTY_STRING, userRepository.userDetails()?.userId ?: EMPTY_STRING,
            property, page, position, isListView)
    }

    override fun trackScreenEvent(screenName: String) {
        facebookConsumerDispatcher.sendScreenEvent(screenName)
        firebaseConsumerDispatcher.sendScreenEvent(screenName)
        snowplowConsumerDispatcher.sendScreenEvent(screenName,
            userRepository.getLoginStatus(),
            userRepository.userDetails()?.userToken ?: EMPTY_STRING,
            userRepository.userDetails()?.userId ?: EMPTY_STRING
        )
        tagManagerDispatcher.screenEvent(screenName, countryRepository.getCurrentCountry())
    }

    override fun trackMasterPushNotificationSettingsEvent(enabled: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //PARAM_KEY_MASTER
    }

    override fun trackSearch(screenName: String, searchFilters: SearchFilters){
        Timber.d("#TEST ANALYTICS NEW# trackSearch")
        snowplowConsumerDispatcher.searchEvent(screenName, userRepository.getLoginStatus(),
            userRepository.userDetails()?.userToken ?: EMPTY_STRING, userRepository.userDetails()?.userId ?: EMPTY_STRING, searchFilters)
        firebaseConsumerDispatcher.sendSearchEvent(screenName, searchFilters)
    }
}