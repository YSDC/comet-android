package com.ysdc.comet.authentication.ui.activity

import android.app.Activity
import com.github.ajalt.timberkt.Timber
import com.ysdc.comet.authentication.R
import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.authentication.model.PhoneAuthenticationStatus.*
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.repositories.ConfigurationRepository
import com.ysdc.comet.repositories.TeamRepository
import com.ysdc.comet.repositories.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by david on 1/25/18.
 */

class AuthenticationPresenter<V : AuthenticationMvpView>(
    errorHandler: ErrorHandler,
    private val phoneAuthenticationManager: PhoneAuthenticationManager,
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository,
    private val configurationRepository: ConfigurationRepository
) : BasePresenter<V>(errorHandler), AuthenticationMvpPresenter<V> {

    override fun initAuthenticationManager(activity: Activity) {
        phoneAuthenticationManager.setActivity(activity)
        subscribeAuthenticationStatus()
    }

    override fun hasTeamCode(): Boolean {
        return userRepository.getUser() != null && userRepository.getUser()!!.teamId != 0
    }

    override fun resendCode() {
        phoneAuthenticationManager.resendVerificationCode(userRepository.getUser()!!.phone)
    }

    private fun subscribeAuthenticationStatus() {
        compositeDisposable.add(
            phoneAuthenticationManager.getAuthenticationStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { status ->
                    when (status) {
                        STATE_EMPTY -> {
                            Timber.d { "Authentication Status: STATE_EMPTY" }
                            mvpView?.initialize()
                        }
                        STATE_INITIALIZED -> {
                            Timber.d { "Authentication Status: STATE_INITIALIZED" }
                            mvpView?.initialisationDone()
                        }
                        STATE_VERIFY_FAILED -> {
                            Timber.d { "Authentication Status: STATE_VERIFY_FAILED" }
                            mvpView?.onError(R.string.error_authentication_verify)
                        }
                        STATE_VERIFY_FAILED_PHONE -> {
                            Timber.d { "Authentication Status: STATE_VERIFY_FAILED_PHONE" }
                            mvpView?.onError(R.string.error_authentication_phone)
                        }
                        STATE_VERIFY_FAILED_QUOTA -> {
                            Timber.d { "Authentication Status: STATE_VERIFY_FAILED_QUOTA" }
                            mvpView?.onError(R.string.error_authentication_quota)
                        }
                        STATE_VERIFY_FAILED_CODE -> {
                            Timber.d { "Authentication Status: STATE_VERIFY_FAILED_CODE" }
                            mvpView?.onVerificationCodeError()
                        }
                        STATE_VERIFY_SUCCESS -> {
                            Timber.d { "Authentication Status: STATE_VERIFY_SUCCESS" }
                            mvpView?.verificationSucceed()
                        }
                        STATE_CODE_SENT -> {
                            Timber.d { "Authentication Status: STATE_CODE_SENT" }
                            mvpView?.codeSent()
                        }
                        STATE_SIGNIN_FAILED -> {
                            Timber.d { "Authentication Status: STATE_SIGNIN_FAILED" }
                            mvpView?.onError(R.string.error_authentication_sign)
                        }
                        STATE_SIGNIN_SUCCESS -> {
                            Timber.d { "Authentication Status: STATE_SIGNIN_SUCCESS" }
                            val user = userRepository.getUser()
                            if(user != null){
                                compositeDisposable.add(
                                    userRepository.createUser(user)
                                        .subscribeOn(Schedulers.io())
                                        .andThen(teamRepository.registerTeam(configurationRepository.getCurrentSeason()))
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                            {mvpView?.authenticationDone()},
                                            {throwable -> mvpView?.onError(throwable)}
                                        )
                                )
                            }
                        }
                    }
                }
        )
    }
}
