package com.ysdc.comet.authentication

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants.TEAM_CODE
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.data.DataManager

class TeamPresenter<V : TeamMvpView>(
    errorHandler: ErrorHandler,
    private val preferences: MyPreferences,
    private val dataManager: DataManager
) : BasePresenter<V>(errorHandler), TeamMvpPresenter<V> {

    override fun getTeamCode(): String? {
        return preferences.getAsString(TEAM_CODE)
    }

    override fun isTeamCodeFormatValid(code: String): Boolean {
        //alphanumerical of length 6
        return code.trim().toUpperCase().matches(Regex("[\\w\\d]{6}"))
    }

    override fun validateTeamCode(code: String) {
        if (isTeamCodeFormatValid(code)) {
            compositeDisposable.add(
                dataManager.teamExist(code).subscribe(
                    { isSuccess ->
                        if (isSuccess) {
                            preferences.setString(TEAM_CODE, code)
                            mvpView?.teamValidated()
                        } else {
                            mvpView?.onError(R.string.error_authentication_team_validation)
                        }
                    }, { throwable ->
                        mvpView?.onError(throwable)
                    })
            )
            mvpView?.teamValidated()
        } else {
            mvpView?.onError(R.string.error_authentication_team_format)
        }
    }
}