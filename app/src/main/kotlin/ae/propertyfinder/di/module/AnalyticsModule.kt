package ae.propertyfinder.di.module

import ae.propertyfinder.R
import ae.propertyfinder.analytics.AnalyticsManagerImpl
import ae.propertyfinder.analytics.AnalyticsManager
import ae.propertyfinder.analytics.dispatcher.FacebookConsumerDispatcher
import ae.propertyfinder.analytics.dispatcher.FirebaseConsumerDispatcher
import ae.propertyfinder.analytics.dispatcher.SnowplowConsumerDispatcher
import ae.propertyfinder.analytics.dispatcher.TagManagerDispatcher
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.repository.CountryRepository
import com.ysdc.comet.common.data.repository.UserRepository
import com.ysdc.comet.common.di.annotation.ApplicationContext
import android.content.Context
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.tagmanager.TagManager
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule {

    @Singleton
    @Provides
    fun provideFacebookDispatcher(@ApplicationContext context: Context) : FacebookConsumerDispatcher{
        return FacebookConsumerDispatcher(AppEventsLogger.newLogger(context))
    }

    @Singleton
    @Provides
    fun provideFirebaseDispatcher(@ApplicationContext context: Context) : FirebaseConsumerDispatcher{
        return FirebaseConsumerDispatcher(FirebaseAnalytics.getInstance(context))
    }

    @Singleton
    @Provides
    fun provideSnowplowDispatcher(@ApplicationContext context: Context, generalConfig: GeneralConfig, countryRepository: CountryRepository) : SnowplowConsumerDispatcher{
        return SnowplowConsumerDispatcher(generalConfig.language(), generalConfig.versionName(), countryRepository.getCurrentCountry()?.code, context, generalConfig.snowplowEmitter(), generalConfig.snowplowEmitterProtocol())
    }

    @Singleton
    @Provides
    fun provideTagManagerDispatcher(@ApplicationContext context: Context, generalConfig: GeneralConfig) : TagManagerDispatcher{
        return TagManagerDispatcher(TagManager.getInstance(context), generalConfig.isDebug(), generalConfig.tagManagerContainerId(), R.raw.gtm_defaultcontainer_gtm_id)
    }

    @Singleton
    @Provides
    fun provideAnalyticsManager(facebookConsumerDispatcher: FacebookConsumerDispatcher, firebaseConsumerDispatcher: FirebaseConsumerDispatcher,
                                snowplowConsumerDispatcher: SnowplowConsumerDispatcher, tagManagerDispatcher: TagManagerDispatcher,
                                userRepository: UserRepository, countryRepository: CountryRepository) : AnalyticsManager {
        return AnalyticsManagerImpl(facebookConsumerDispatcher, firebaseConsumerDispatcher, snowplowConsumerDispatcher, tagManagerDispatcher, userRepository, countryRepository)
    }
}
