package com.ysdc.comet.common.ui.base


import com.ysdc.comet.common.data.ErrorHandler
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by david on 26/2/18.
 */

abstract class BasePresenter<V : MvpView>(protected val errorHandler: ErrorHandler) : MvpPresenter<V> {

    var mvpView: V? = null
        private set
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        subscribeErrors()
    }

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
        if(compositeDisposable.isDisposed) {
            this.compositeDisposable = CompositeDisposable()
        }
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        mvpView = null
    }

    private fun subscribeErrors() {
        compositeDisposable.add(errorHandler.subscribeGeneralError().subscribe { errorMessage -> mvpView!!.onError(errorMessage) })
    }

}