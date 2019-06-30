package com.ysdc.comet.ui.splashscreen

import com.ysdc.comet.common.ui.base.MvpPresenter


/**
 * Created by david on 26/2/18.
 */

interface SplashMvpPresenter<V : SplashMvpView> : MvpPresenter<V> {
    fun loadConfiguration()
    fun versionDialogClosed()
}
