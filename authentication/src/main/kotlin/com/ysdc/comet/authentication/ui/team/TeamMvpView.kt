package com.ysdc.comet.authentication.ui.team

import com.ysdc.comet.common.ui.base.MvpView


interface TeamMvpView : MvpView {
    fun teamValidated()
    fun displayError(messageId : Int)
}