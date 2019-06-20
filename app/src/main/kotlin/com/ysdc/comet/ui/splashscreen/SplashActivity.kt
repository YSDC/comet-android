package com.ysdc.comet.ui.splashscreen

import android.os.Bundle
import com.ysdc.comet.R
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.ui.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
        compositeDisposable.add(
            presenter.fakeLoad()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, { t: Throwable? ->
                    t?.let { onError(it) }
                }, { openAuthenticationActivity() })
//            presenter.loadConfiguration()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    openHomeActivity()
//                }, { t: Throwable? ->
//                    t?.let { onError(it) }
//                })
        )
    }

    override fun openHomeActivity() {
        TODO("not implemented")
//        startActivity(MainActivity.newInstance(this))
//        finish()
    }

    override fun openAuthenticationActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initView() {
        app_version.text = getString(R.string.settings_version, appConfig.versionName(), appConfig.versionCode())
    }
}
