package ae.propertyfinder.data.repository

import ae.propertyfinder.model.LoginStatus
import ae.propertyfinder.model.UserDetails
import com.ysdc.comet.common.data.prefs.PrefsConstants
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.data.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class UserRepositoryImpl(private val propertyFinderPreferences: PropertyFinderPreferences) : UserRepository {

    private val loginStatus: BehaviorSubject<LoginStatus> = BehaviorSubject.createDefault(
        LoginStatus.fromProvider(
            propertyFinderPreferences.getAsString(PrefsConstants.USER_LOGIN_STATUS, LoginStatus.NOT_LOGGED.provider)
        )
    )
    override fun getLoginStatus(): LoginStatus {
        return LoginStatus.fromProvider(propertyFinderPreferences.getAsString(PrefsConstants.USER_LOGIN_STATUS, LoginStatus.NOT_LOGGED.provider))
    }
    override fun getObservableLoginStatus(): Observable<LoginStatus>{
        return loginStatus
    }

    override fun userDetails(): UserDetails? {
        return null
    }

    override fun login(loginStatus: LoginStatus, token: String): Single<UserDetails> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun login(username: String, password: String): Single<UserDetails> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateLoginStatus(status: LoginStatus) {
        if (status == LoginStatus.NOT_LOGGED) {
            logout()
        }
        propertyFinderPreferences.setString(PrefsConstants.USER_LOGIN_STATUS, status.provider)
        loginStatus.onNext(status)

    }

    override fun logout() {
        eraseUserDetails()
    }

    private fun eraseUserDetails() {
        //TODO
    }

}