package com.ysdc.comet.authentication.ui.activity

import android.os.Bundle
import com.ysdc.comet.authentication.R
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.ui.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by david on 1/25/18.
 */

class AuthenticationActivity : BaseActivity(), AuthenticationMvpView {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    internal lateinit var presenter: AuthenticationMvpPresenter<AuthenticationMvpView>
    @Inject
    internal lateinit var appConfig: GeneralConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        presenter.onAttach(this)

        initView()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        presenter.onDetach()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initView() {

    }
}
