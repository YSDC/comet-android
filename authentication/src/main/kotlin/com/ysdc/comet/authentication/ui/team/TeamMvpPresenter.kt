package com.ysdc.comet.authentication

import com.ysdc.comet.common.ui.base.MvpPresenter

interface TeamMvpPresenter<V : TeamMvpView> : MvpPresenter<V> {
    /**
     * Verify that the team code as the expected format
     */
    fun isTeamCodeFormatValid(code : String) : Boolean

    /**
     * Return the team code if we have one
     */
    fun getTeamCode(): String?
}