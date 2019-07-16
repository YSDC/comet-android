package com.ysdc.comet.authentication.ui.register

import com.ysdc.comet.authentication.R
import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.common.utils.FormatterUtils
import com.ysdc.comet.common.utils.ValidationUtils
import com.ysdc.comet.model.User
import com.ysdc.comet.model.UserRole
import com.ysdc.comet.repositories.UserRepository

class RegisterPresenter<V : RegisterMvpView>(
    errorHandler: ErrorHandler,
    private val preferences: MyPreferences,
    private val validationUtils: ValidationUtils,
    private val phoneAuthenticationManager: PhoneAuthenticationManager,
    private val formatterUtils: FormatterUtils,
    private val userRepository: UserRepository
) : BasePresenter<V>(errorHandler), RegisterMvpPresenter<V> {

    private lateinit var user: User

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        user = userRepository.getUser()!!
        phoneAuthenticationManager.initialize(user.phone)
    }

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

    override fun isRoleValid(role: UserRole): Boolean {
        return validationUtils.isRoleValid(role)
    }

    override fun getRoles(): Array<String> {
        return mvpView?.let { view ->
            UserRole.values().map { view.provideResources().getString(it.key) }.toTypedArray()
        } ?: arrayOf()
    }

    override fun getUser(): User {
        return user
    }

    override fun updateUser() {
        userRepository.updateUserLocally(user)
    }

    override fun startAuthentication() {
        mvpView?.displayLoading(R.string.phone_code_request)
        phoneAuthenticationManager.startAuthentication(user.phone)
    }
}