package com.ysdc.comet.ui.splashscreen

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by david on 1/25/18.
 */

class SplashPresenter<V : SplashMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), SplashMvpPresenter<V> {

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
    }

    override fun loadConfiguration(): Completable {
        TODO("not implemented")
    }

    override fun fakeLoad() : Observable<Long> {
            return Observable.interval(3, TimeUnit.SECONDS)
    }
}
