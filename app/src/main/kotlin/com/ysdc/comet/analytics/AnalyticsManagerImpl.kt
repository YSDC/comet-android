package com.ysdc.comet.analytics

import com.ysdc.comet.analytics.dispatcher.FirebaseDispatcher
import com.ysdc.comet.analytics.dispatcher.GoogleAnalyticsDispatcher


class AnalyticsManagerImpl(
    private val firebaseConsumerDispatcher: FirebaseDispatcher,
    private val googleAnalyticsDispatcher: GoogleAnalyticsDispatcher
) : AnalyticsManager {

}