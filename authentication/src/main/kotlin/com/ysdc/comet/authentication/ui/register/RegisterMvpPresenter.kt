package com.ysdc.comet.authentication.ui.register

import com.ysdc.comet.common.ui.base.MvpPresenter
import com.ysdc.comet.model.User
import com.ysdc.comet.model.UserRole

interface RegisterMvpPresenter<V : RegisterMvpView> : MvpPresenter<V> {
    fun isFirstNameValid(value : String) : Boolean
    fun isLastNameValid(value : String) : Boolean
    fun isPhoneValid(value : String) : Boolean
    fun isEmailValid(value : String) : Boolean
    fun isRoleValid(role : UserRole) : Boolean
    fun getRoles(): Array<String>
    fun startAuthentication()
    fun updateUser()
    fun getUser(): User
}