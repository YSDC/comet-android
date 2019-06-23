package com.ysdc.comet.authentication.ui.register

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.common.utils.ProfileValidationUtils
import com.ysdc.comet.model.UserRole

class RegisterPresenter<V : RegisterMvpView>(
    errorHandler: ErrorHandler,
    private val validationUtils: ProfileValidationUtils
) : BasePresenter<V>(errorHandler), RegisterMvpPresenter<V> {
    override fun isFirstNameValid(value: String): Boolean {
        return validationUtils.isFirstNameValid(value)
    }

    override fun isLastNameValid(value: String): Boolean {
        return validationUtils.isLastNameValid(value)
    }

    override fun isPhoneValid(value: String): Boolean {
        return validationUtils.isPhoneValid(value)
    }

    override fun isEmailValid(value: String): Boolean {
        return validationUtils.isEmailValid(value)
    }

    override fun isRoleValid(role: UserRole) {
        return validationUtils.isRoleValid(role)
    }
}