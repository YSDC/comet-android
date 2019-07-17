package com.ysdc.comet.authentication.ui.team

import com.ysdc.comet.authentication.R
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.common.utils.ValidationUtils
import com.ysdc.comet.model.Team
import com.ysdc.comet.model.User
import com.ysdc.comet.repositories.ConfigurationRepository
import com.ysdc.comet.repositories.TeamRepository
import com.ysdc.comet.repositories.UserRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeamPresenter<V : TeamMvpView>(
    errorHandler: ErrorHandler,
    private val userRepository: UserRepository,
    private val validationUtils: ValidationUtils,
    private val teamRepository: TeamRepository,
    private val configurationRepository: ConfigurationRepository,
    private val generalConfig: GeneralConfig
) : BasePresenter<V>(errorHandler), TeamMvpPresenter<V> {

    private var teams: List<Team>? = null
    private var teamSelected: Team? = null
    private var user: User = userRepository.getUser() ?: User()

    override fun isTeamCodeFormatValid(code: String): Boolean {
        return validationUtils.isTeamCodeValid(code)
    }

    override fun validateTeamCode(code: String) {
        if (teamSelected == null) {
            mvpView?.displayError(R.string.error_team_missing)
        } else if (!isTeamCodeFormatValid(code)) {
            mvpView?.displayError(R.string.error_authentication_team_format)
        } else {
            compositeDisposable.add(
                teamRepository.validateActivationCode(code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { mvpView?.displayLoading(R.string.team_code_verification) }
                    .subscribe(
                        { isSuccess ->
                            if (isSuccess) {
                                user.teamId = teamSelected!!.teamId
                                userRepository.updateUserLocally(user)
                                teamRepository.updateTeamLocally(teamSelected!!)
                                mvpView?.teamValidated()
                            } else {
                                mvpView?.displayError(R.string.error_authentication_team_validation)
                            }
                        }, { throwable ->
                            mvpView?.hideAlert()
                            mvpView?.onError(throwable)
                        })
            )
        }
    }

    override fun loadTeams(): Single<List<String>> {
        return teamRepository.getAvailableTeams(generalConfig.clubId(),configurationRepository.getCurrentSeason())
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