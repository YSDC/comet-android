package com.ysdc.comet.analytics.dispatcher

import com.google.android.gms.analytics.Tracker

class GoogleAnalyticsDispatcher(private val tracker: Tracker) {

    init {
        tracker.enableAdvertisingIdCollection(true)
        tracker.enableAutoActivityTracking(false)
        tracker.enableExceptionReporting(false)
    }

}
