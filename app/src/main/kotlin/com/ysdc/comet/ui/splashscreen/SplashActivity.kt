package com.ysdc.comet.ui.splashscreen

import android.os.Bundle
import com.ysdc.comet.R
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.navigation.NavigationManager
import com.ysdc.comet.common.ui.base.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject


/**
 * Created by david on 1/25/18.
 */

class SplashActivity : BaseActivity(), SplashMvpView {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    internal lateinit var presenter: SplashMvpPresenter<SplashMvpView>
    @Inject
    internal lateinit var appConfig: GeneralConfig
    @Inject
    internal lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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
        presenter.loadConfiguration()
    }

    override fun openHomeActivity() {
        navigationManager.displayMainView(this)
    }

    override fun openAuthenticationActivity() {
        navigationManager.displayAuthenticationView(this)
    }

    private fun initView() {
        app_version.text = getString(R.string.settings_version, appConfig.versionName(), appConfig.versionCode())
    }
}
