package com.ysdc.comet.authentication.ui.team

import com.ysdc.comet.common.ui.base.MvpPresenter
import io.reactivex.Single

interface TeamMvpPresenter<V : TeamMvpView> : MvpPresenter<V> {
    /**
     * Verify that the team code as the expected format
     */
    fun isTeamCodeFormatValid(code: String): Boolean

    /**
     * Verify that the team code exist, or raise an error in case it doesn't
     */
    fun validateTeamCode(code: String)

    fun setTeamSelected(index: Int)

    fun loadTeams(): Single<List<String>>
}