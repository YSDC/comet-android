package ae.propertyfinder.data.repository

import com.ysdc.comet.common.application.GeneralConfig
import ae.propertyfinder.model.PushNotificationStatus
import com.ysdc.comet.common.data.prefs.PrefsConstants.PUSH_NOTIFICATION_STATUS
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.data.repository.NotificationRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class NotificationRepositoryImpl(private val propertyFinderPreferences: PropertyFinderPreferences, private val generalConfig: GeneralConfig) :
    NotificationRepository {

    private val pushNotificationUpdate: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private var pushNotificationStatus: PushNotificationStatus = loadPushNotificationStatus()

    override fun updateMasterPushNotificationStatus(isEnabled: Boolean): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPushNotificationStatus(): Single<PushNotificationStatus> {
        return Single.defer {
            pushNotificationUpdate
                .filter { pushNotificationUpdate -> !pushNotificationUpdate }
                .map { pushNotificationStatus }
                .firstOrError()
        }
    }

    override fun updateWatsonPushAttribut(newStatus: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadPushNotificationStatus(): PushNotificationStatus {
        return propertyFinderPreferences.getAsString(PUSH_NOTIFICATION_STATUS)?.let {
            return generalConfig.getMoshi().adapter<PushNotificationStatus>(PushNotificationStatus::class.java).fromJson(it)!!
        } ?: PushNotificationStatus()
    }
}