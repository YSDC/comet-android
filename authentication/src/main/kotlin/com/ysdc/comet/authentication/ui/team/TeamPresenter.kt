package com.ysdc.comet.authentication

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants.TEAM_CODE
import com.ysdc.comet.common.ui.base.BasePresenter

class TeamPresenter<V : TeamMvpView>(
    errorHandler: ErrorHandler,
    private val preferences: MyPreferences
) : BasePresenter<V>(errorHandler), TeamMvpPresenter<V> {

    override fun getTeamCode(): String? {
        return preferences.getAsString(TEAM_CODE)
    }

    override fun isTeamCodeFormatValid(code: String): Boolean {
        //alphanumerical of length 6
        return code.trim().toUpperCase().matches(Regex("[\\w\\d]{6}"))
    }


}