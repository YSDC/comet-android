package com.ysdc.comet.ui.splashscreen

import com.ysdc.comet.common.ui.base.MvpView
import com.ysdc.comet.model.Team


/**
 * Created by david on 1/25/18.
 */

interface SplashMvpView : MvpView {
    fun openHomeActivity()
    fun openAuthenticationActivity()
}
