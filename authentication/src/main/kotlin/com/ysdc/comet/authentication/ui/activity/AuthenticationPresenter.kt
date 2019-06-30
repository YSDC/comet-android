package com.ysdc.comet.authentication.ui.activity

import android.app.Activity
import com.ysdc.comet.authentication.R
import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.authentication.model.PhoneAuthenticationStatus.*
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants
import com.ysdc.comet.common.data.prefs.PrefsConstants.TEAM_CODE
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.common.utils.AppConstants
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by david on 1/25/18.
 */

class AuthenticationPresenter<V : AuthenticationMvpView>(
    errorHandler: ErrorHandler,
    private val phoneAuthenticationManager: PhoneAuthenticationManager,
    private val preferences: MyPreferences
) : BasePresenter<V>(errorHandler), AuthenticationMvpPresenter<V> {

    override fun initAuthenticationManager(activity: Activity) {
        phoneAuthenticationManager.setActivity(activity)
        subscribeAuthenticationStatus()
    }

    override fun hasTeamCode(): Boolean {
        return preferences.getAsString(TEAM_CODE, EMPTY_STRING).isNotEmpty()
    }

    override fun resendCode(){
        phoneAuthenticationManager.resendVerificationCode(preferences.getAsString(PrefsConstants.USER_PHONE, EMPTY_STRING))
    }

    override fun updateUserDetails() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun subscribeAuthenticationStatus() {
        compositeDisposable.add(
            phoneAuthenticationManager.getAuthenticationStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { status ->
                    when (status) {
                        STATE_EMPTY -> {
                            mvpView?.initialize()
                        }
                        STATE_INITIALIZED -> {
                            mvpView?.initialisationDone()
                        }
                        STATE_VERIFY_FAILED -> {
                            mvpView?.onError(R.string.error_authentication_verify)
                        }
                        STATE_VERIFY_FAILED_PHONE -> {
                            mvpView?.onError(R.string.error_authentication_phone)
                        }
                        STATE_VERIFY_FAILED_QUOTA -> {
                            mvpView?.onError(R.string.error_authentication_quota)
                        }
                        STATE_VERIFY_FAILED_CODE -> {
                            mvpView?.onVerificationCodeError()
                        }
                        STATE_VERIFY_SUCCESS -> {
                            mvpView?.verificationSucceed()
                        }
                        STATE_CODE_SENT -> {
                            mvpView?.codeSent()
                        }
                        STATE_SIGNIN_FAILED -> {
                            mvpView?.onError(R.string.error_authentication_sign)
                        }
                        STATE_SIGNIN_SUCCESS -> {
                            mvpView?.authenticationDone()
                        }
                    }
                }
        )
    }
}
