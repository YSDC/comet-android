package com.ysdc.comet.ui.splashscreen

import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants.TEAM_CODE
import com.ysdc.comet.common.data.prefs.PrefsConstants.TEAM_ID
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.model.Team
import com.ysdc.comet.repository.TeamRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by david on 1/25/18.
 */

class SplashPresenter<V : SplashMvpView>(
    errorHandler: ErrorHandler,
    private val preferences: MyPreferences,
    private val phoneAuthenticationManager: PhoneAuthenticationManager,
    private val teamRepository: TeamRepository
    ) : BasePresenter<V>(errorHandler), SplashMvpPresenter<V> {

    private var teams : List<Team>? = null
    private var teamSelected : Team? = null

    override fun loadConfiguration() {
        if (preferences.contains(TEAM_CODE)) {
            compositeDisposable.add(
                Observable.interval(3, TimeUnit.SECONDS)
                    .filter { it < 3 }
                    .firstOrError()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { _ ->
                        if (phoneAuthenticationManager.isLoggedIn()) {
                            mvpView?.openHomeActivity()
                        } else {
                            mvpView?.openAuthenticationActivity()
                        }
                    }
            )
        } else {
            compositeDisposable.add(
                teamRepository.getAvailableTeams()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { newTeams ->
                        this.teams = newTeams
                        newTeams.map { it.name }.toList()
                    }
                    .subscribe({ newTeams ->
                        mvpView?.showSelector(newTeams)
                    }, { throwable ->
                        mvpView?.onError(throwable)
                    })
            )

        }
    }

    override fun setTeamSelected(index: Int) {
        teamSelected = teams?.get(index)
    }

    override fun goToNextStep(){
        if(teamSelected != null){
            preferences.setInt(TEAM_ID, teamSelected!!.id)
            mvpView?.openAuthenticationActivity()
        }else{
            //TODO display error message
        }
    }
}
