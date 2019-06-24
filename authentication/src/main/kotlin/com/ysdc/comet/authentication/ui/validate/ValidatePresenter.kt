package com.ysdc.comet.authentication

import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.common.utils.AppConstants

class ValidatePresenter<V : ValidateMvpView>(
    errorHandler: ErrorHandler,
    private val preferences: MyPreferences,
    private val phoneAuthenticationManager: PhoneAuthenticationManager
) : BasePresenter<V>(errorHandler), ValidateMvpPresenter<V> {

    override fun validateCode(code : String){
        phoneAuthenticationManager.verifyPhoneNumberWithCode(code)
    }
}