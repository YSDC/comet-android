package com.ysdc.comet.authentication.ui.register

import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants.USER_FIRSTNAME
import com.ysdc.comet.common.data.prefs.PrefsConstants.USER_LASTNAME
import com.ysdc.comet.common.data.prefs.PrefsConstants.USER_MAIL
import com.ysdc.comet.common.data.prefs.PrefsConstants.USER_PHONE
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.common.utils.AppConstants
import com.ysdc.comet.common.utils.ProfileValidationUtils
import com.ysdc.comet.model.UserRole

class RegisterPresenter<V : RegisterMvpView>(
    errorHandler: ErrorHandler,
    private val preferences: MyPreferences,
    private val validationUtils: ProfileValidationUtils,
    private val phoneAuthenticationManager: PhoneAuthenticationManager
) : BasePresenter<V>(errorHandler), RegisterMvpPresenter<V> {

    private var roleSelected: UserRole = UserRole.UNDEFINED

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        phoneAuthenticationManager.initialize(preferences.getAsString(USER_PHONE, AppConstants.EMPTY_STRING))
    }

    override fun getFirstName() : String {
        return preferences.getAsString(USER_FIRSTNAME, AppConstants.EMPTY_STRING)
    }

    override fun getLastName() : String {
        return preferences.getAsString(USER_LASTNAME, AppConstants.EMPTY_STRING)
    }

    override fun getPhone() : String {
        return preferences.getAsString(USER_PHONE, AppConstants.EMPTY_STRING)
    }

    override fun getEmail() : String {
        return preferences.getAsString(USER_MAIL, AppConstants.EMPTY_STRING)
    }

    override fun setFirstName(value : String) {
        return preferences.setString(USER_FIRSTNAME, value)
    }

    override fun setLastName(value : String) {
        return preferences.setString(USER_LASTNAME, value)
    }

    override fun setPhone(value : String) {
        return preferences.setString(USER_PHONE, value)
    }

    override fun setEmail(value : String) {
        return preferences.setString(USER_MAIL, value)
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

    override fun isRoleValid(role: UserRole) : Boolean {
        return validationUtils.isRoleValid(role)
    }

    override fun getRoles(): Array<String> {
        return mvpView?.let { view ->
            UserRole.values().map { view.provideResources().getString(it.key) }.toTypedArray()
        } ?: arrayOf()
    }

    override fun getIndexRoleSelected(): Int {
        return roleSelected.ordinal
    }

    override fun getRoleSelected(): UserRole {
        return roleSelected
    }

    override fun setRoleSelected(index: Int) {
        roleSelected = UserRole.values()[index]
    }

    override fun startAuthentication(){
        phoneAuthenticationManager.startAuthentication(preferences.getAsString(USER_PHONE, AppConstants.EMPTY_STRING))
    }
}