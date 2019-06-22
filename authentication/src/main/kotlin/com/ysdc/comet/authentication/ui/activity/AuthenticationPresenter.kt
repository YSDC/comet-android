package com.ysdc.comet.authentication.ui.activity

import android.app.Activity
import com.ysdc.comet.authentication.R
import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.authentication.model.PhoneAuthenticationStatus.*
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by david on 1/25/18.
 */

class AuthenticationPresenter<V : AuthenticationMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), AuthenticationMvpPresenter<V> {

    private lateinit var authenticationManager: PhoneAuthenticationManager

    override fun initAuthenticationManager(activity: Activity) {
        authenticationManager = PhoneAuthenticationManager(activity)

        subscribeAuthenticationStatus()
    }

    private fun subscribeAuthenticationStatus() {
        compositeDisposable.add(
            authenticationManager.getAuthenticationStatus()
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
                            mvpView?.onError(R.string.error_authentication_code)
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
