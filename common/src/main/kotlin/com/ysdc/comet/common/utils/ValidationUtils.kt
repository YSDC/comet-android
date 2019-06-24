package com.ysdc.comet.common.utils

import android.util.Patterns
import com.ysdc.comet.model.UserRole

class ValidationUtils {

    fun isTeamCodeValid(value : String): Boolean {
        return value.trim().toUpperCase().matches(Regex("[\\w\\d]{6}"))
    }

    fun isFirstNameValid(value: String): Boolean {
        return value.isNotEmpty() && value.matches(Regex("[a-zA-Z]+"))
    }

    fun isLastNameValid(value: String): Boolean {
        return value.isNotEmpty() && value.matches(Regex("[a-zA-Z]+"))
    }

    fun isPhoneValid(value: String): Boolean {
        return value.isNotEmpty() && Patterns.PHONE.matcher(value).matches()
    }

    fun isEmailValid(value: String): Boolean {
        return value.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

    fun isRoleValid(role: UserRole) : Boolean {
        return role != UserRole.UNDEFINED
    }
}