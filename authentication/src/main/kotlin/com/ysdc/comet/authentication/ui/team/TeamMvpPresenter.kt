package com.ysdc.comet.authentication.ui.team

import com.ysdc.comet.common.ui.base.MvpPresenter
import io.reactivex.Completable

interface TeamMvpPresenter<V : TeamMvpView> : MvpPresenter<V> {
    /**
     * Verify that the team code as the expected format
     */
    fun isTeamCodeFormatValid(code : String) : Boolean

    /**
     * Return the team code if we have one
     */
    fun getTeamCode(): Int?

    /**
     * Verify that the team code exist, or raise an error in case it doesn't
     */
    fun validateTeamCode(code: String)
}