package com.ysdc.comet.authentication.ui.team

import com.ysdc.comet.authentication.BuildConfig
import com.ysdc.comet.authentication.R
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants.TEAM_CODE
import com.ysdc.comet.common.exception.ValidationException
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import com.ysdc.comet.common.utils.ValidationUtils
import com.ysdc.comet.data.DataManager
import com.ysdc.comet.model.Team
import com.ysdc.comet.repositories.TeamRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TeamPresenter<V : TeamMvpView>(
    errorHandler: ErrorHandler,
    private val preferences: MyPreferences,
    private val dataManager: DataManager,
    private val validationUtils: ValidationUtils,
    private val teamRepository: TeamRepository
) : BasePresenter<V>(errorHandler), TeamMvpPresenter<V> {

    private var teams : List<Team>? = null
    private var teamSelected : Team? = null

    override fun getTeamCode(): String? {
        return preferences.getAsString(TEAM_CODE)
    }

    override fun isTeamCodeFormatValid(code: String): Boolean {
        return validationUtils.isTeamCodeValid(code)
    }

    override fun validateTeamCode(code: String) {
        if (isTeamCodeFormatValid(code)) {
            compositeDisposable.add(
                dataManager.teamExist(code)
                    .doOnSubscribe { mvpView?.displayLoading(R.string.team_code_verification) }
                    .subscribe(
                        { isSuccess ->
                            if (isSuccess) {
                                preferences.setString(TEAM_CODE, code)
                                mvpView?.teamValidated()
                            } else {
                                mvpView?.displayError(R.string.error_authentication_team_validation)
                            }
                        }, { throwable ->
                            mvpView?.hideAlert()
                            mvpView?.onError(throwable)
                        })
            )
        } else {
            mvpView?.displayError(R.string.error_authentication_team_format)
        }
    }

    override fun loadTeams() : Single<List<String>> {
        return teamRepository.getAvailableTeams(BuildConfig.CLUB_CODE)
            .subscribeOn(Schedulers.io())
            .map { newTeams ->
                this.teams = newTeams
                newTeams.map { it.name }.toList()
            }
    }

    override fun setTeamSelected(index: Int) {
        teamSelected = teams?.get(index)
    }
}