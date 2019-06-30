package com.ysdc.comet.ui.splashscreen

import com.ysdc.comet.R
import com.ysdc.comet.authentication.manager.PhoneAuthenticationManager
import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter
import com.ysdc.comet.data.model.AppUpdateStatus
import com.ysdc.comet.repositories.ConfigurationRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by david on 1/25/18.
 */

class SplashPresenter<V : SplashMvpView>(
    errorHandler: ErrorHandler,
    private val phoneAuthenticationManager: PhoneAuthenticationManager,
    private val configurationRepository: ConfigurationRepository
) : BasePresenter<V>(errorHandler), SplashMvpPresenter<V> {


    override fun loadConfiguration() {
        compositeDisposable.add(
            configurationRepository.appUpdateStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .firstOrError()
                .flatMapCompletable { status ->
                    when (status) {
                        AppUpdateStatus.RECOMMENDED -> {
                            if (configurationRepository.shouldDisplayRecommandedDialog()) {
                                mvpView?.showVersionDialog(R.string.recommended_update_title, R.string.recommended_update_message, true)
                            }
                            Completable.complete()
                        }
                        AppUpdateStatus.REQUIRED -> {
                            mvpView?.showVersionDialog(R.string.required_update_title, R.string.required_update_message, false)
                            Completable.complete()
                        }
                        else -> {
                            loadData()
                        }
                    }
                }
                .subscribe()
        )
    }

    override fun versionDialogClosed() {
        configurationRepository.saveRecommendedUpdateDialogSeen()
        compositeDisposable.add(loadData().subscribe())
    }

    private fun loadData(): Completable {
        return Observable.interval(3, TimeUnit.SECONDS)
            .filter { it < 3 && it > 4 }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .firstOrError()
            .flatMapCompletable {
                if (phoneAuthenticationManager.isLoggedIn()) {
                    mvpView?.openHomeActivity()
                } else {
                    mvpView?.openAuthenticationActivity()
                }
                Completable.complete()
            }
    }
}
