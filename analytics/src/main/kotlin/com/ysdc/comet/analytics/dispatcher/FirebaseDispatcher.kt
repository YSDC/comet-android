package com.ysdc.comet.analytics.dispatcher

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseDispatcher(private val firebase: FirebaseAnalytics) {

    private fun sendEvent(eventName: String, bundle: Bundle?) {
        firebase.logEvent(eventName, bundle)
    }
}
