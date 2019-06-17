package ae.propertyfinder.application

import ae.propertyfinder.BuildConfig
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.utils.AppConstants
import ae.propertyfinder.model.ProjectDimension
import ae.propertyfinder.model.Version
import ae.propertyfinder.utils.ConsumerAppConstants.PREFERENCES_FILENAME
import android.app.Application
import android.content.pm.PackageManager
import com.snowplowanalytics.snowplow.tracker.emitter.RequestSecurity
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.util.*

/**
 * Implementation of the generalConfig interface
 * @see GeneralConfig
 */
class AppConfig(private val application: Application) : GeneralConfig {

    override fun localeString(): String {
        val language = Locale.getDefault().language
        return if (language.equals(BuildConfig.PRIMARY_LANGUAGE, ignoreCase = true)) {
            BuildConfig.PRIMARY_LANGUAGE
        } else {
            BuildConfig.DEFAULT_LANGUAGE
        }
    }

    override fun applicationId(): String {
        return BuildConfig.APPLICATION_ID
    }

    override fun formattedVersion(): String {
        return "v${BuildConfig.VERSION_NAME} #${BuildConfig.VERSION_CODE}"
    }

    override fun appFlyerKey(): String {
        return BuildConfig.APP_FLYER_KEY
    }

    override fun tagManagerContainerId(): String {
        return BuildConfig.GTM_CONTAINER_ID
    }

    override fun flavor(): String {
        return BuildConfig.FLAVOR
    }

    override fun dimension(): ProjectDimension {
        return ProjectDimension.fromId(BuildConfig.FLAVOR_server)
    }

    override fun versionName(): String {
        try {
            return application.packageManager.getPackageInfo(application.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e, "getApplicationVersionName(): %s", e.message)
        }

        return AppConstants.EMPTY_STRING
    }

    override fun versionCode(): Int {
        try {
            return application.packageManager.getPackageInfo(application.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e, "getVersionCode(): %s", e.message)
        }

        return 0
    }

    override fun currentAppVersion(): Version {
        return Version(BuildConfig.VERSION_NAME)
    }

    override fun language(): String {
        return Locale.getDefault().language
    }

    override fun storeUrl(): String {
        return BuildConfig.ANDROID_STORE_URL
    }

    override fun snowplowEmitter(): String {
        return BuildConfig.SNOWPLOW_EMITTER
    }

    override fun snowplowEmitterProtocol(): RequestSecurity {
        return BuildConfig.SNOWPLOW_EMITTER_PROTOCOL
    }

    override fun getPreferencesFileName(): String {
        return PREFERENCES_FILENAME
    }

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun defaultTabIndex(): Int {
        return BuildConfig.DEFAULT_TAB
    }

    override fun getMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}