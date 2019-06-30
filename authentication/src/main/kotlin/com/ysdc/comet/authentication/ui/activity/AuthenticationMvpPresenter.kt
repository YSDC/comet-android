package com.ysdc.comet.authentication.ui.activity

import android.app.Activity
import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.common.ui.base.MvpPresenter
import io.reactivex.Completable
import io.reactivex.Observable


interface AuthenticationMvpPresenter<V : AuthenticationMvpView> : MvpPresenter<V> {

    fun initAuthenticationManager(activity: Activity)
    fun hasTeamCode() : Boolean
    fun resendCode()
    fun updateUserDetails()
}
