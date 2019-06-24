package com.ysdc.comet.authentication.ui.activity

import com.ysdc.comet.common.ui.base.MvpView

interface AuthenticationMvpView : MvpView {

    fun initialize()
    fun initialisationDone()
    fun verificationSucceed()
    fun codeSent()
    fun authenticationDone()
    fun onVerificationCodeError()

}
