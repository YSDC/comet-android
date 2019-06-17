package ae.propertyfinder.analytics.dispatcher

import ae.propertyfinder.analytics.AnalyticsConstants
import android.os.Bundle
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.tagmanager.ContainerHolder
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber

class FirebaseDispatcher(private val firebase: FirebaseAnalytics) {

    private fun sendEvent(eventName: String, bundle: Bundle?) {
        firebase.logEvent(eventName, bundle)
    }
}
