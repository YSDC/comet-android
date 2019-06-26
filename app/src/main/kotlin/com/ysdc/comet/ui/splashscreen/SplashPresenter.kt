package com.ysdc.comet.ui.splashscreen

import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.repositories.TeamRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by david on 1/25/18.
 */

class SplashPresenter<V : SplashMvpView>(
    errorHandler: ErrorHandler,
    private val phoneAuthenticationManager: PhoneAuthenticationManager
) : BasePresenter<V>(errorHandler), SplashMvpPresenter<V> {


    override fun loadConfiguration() {
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
    }
}
