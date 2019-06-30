package com.ysdc.comet.di.module

import android.content.Context
import com.google.android.gms.analytics.Tracker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ysdc.comet.analytics.AnalyticsManager
import com.ysdc.comet.analytics.AnalyticsManagerImpl
import com.ysdc.comet.analytics.dispatcher.FirebaseDispatcher
import com.ysdc.comet.analytics.dispatcher.GoogleAnalyticsDispatcher
import com.ysdc.comet.common.di.annotation.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule {

    @Singleton
    @Provides
    fun provideFirebaseDispatcher(@ApplicationContext context: Context): FirebaseDispatcher {
        return FirebaseDispatcher(FirebaseAnalytics.getInstance(context))
    }

    @Singleton
    @Provides
    fun provideGoogleAnalyticsDispatcher(tracker: Tracker): GoogleAnalyticsDispatcher {
        return GoogleAnalyticsDispatcher(tracker)
    }

    @Singleton
    @Provides
    fun provideAnalyticsManager(firebaseDispatcher: FirebaseDispatcher, googleAnalyticsDispatcher: GoogleAnalyticsDispatcher): AnalyticsManager {
        return AnalyticsManagerImpl(
            firebaseDispatcher,
            googleAnalyticsDispatcher
        )
    }
}
