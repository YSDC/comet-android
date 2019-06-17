package ae.propertyfinder.data.repository

import com.ysdc.comet.common.application.GeneralConfig
import ae.propertyfinder.model.UserProperty
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.data.repository.PropertyRepository
import com.ysdc.comet.common.data.repository.UserRepository
import ae.propertyfinder.model.LoginStatus
import ae.propertyfinder.search.network.SearchNetworkServiceCreator
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kotlin.random.Random

class PropertyRepositoryImp(
    private val searchNetworkServiceCreator: SearchNetworkServiceCreator,
    private val preferences: PropertyFinderPreferences,
    private val userRepository: UserRepository,
    private val generalConfig: GeneralConfig
) : PropertyRepository {

    override fun getUserPropertiesId(): Single<List<Int>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isUserProperty(propertyId: Int): Boolean {
        //TODO Random Boolean testing purpose
        return Random.nextBoolean()
    }

    override fun getLoginStatus(): Observable<LoginStatus> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUserProperties(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addUserProperty(userProperty: UserProperty) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUserProperty(userProperty: UserProperty): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logoutUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateLoginStatus(loginStatus: LoginStatus) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshToken(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}