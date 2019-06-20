package com.ysdc.comet.authentication.ui.activity

import com.ysdc.comet.common.ui.base.MvpPresenter
import io.reactivex.Completable
import io.reactivex.Observable


interface AuthenticationMvpPresenter<V : AuthenticationMvpView> : MvpPresenter<V> {
}
