package com.ysdc.comet.ui.splashscreen

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ysdc.comet.R
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.navigation.NavigationManager
import com.ysdc.comet.common.ui.base.BaseActivity
import com.ysdc.comet.model.Team
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.activity_splash.validate_btn
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by david on 1/25/18.
 */

class SplashActivity : BaseActivity(), SplashMvpView {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

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

    override fun showSelector(teams: List<String>) {
        //TODO animation to show the welcome msg, the title, the spinner and the button

        val teamsAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, teams)
        team_selection_spinner.adapter = teamsAdapter
        team_selection_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, index: Int, l: Long) {
                presenter.setTeamSelected(index)
                validate_btn.visibility = VISIBLE
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                Timber.d("Role: nothing selected")
            }
        }
    }

    private fun initView() {
        app_version.text = getString(R.string.settings_version, appConfig.versionName(), appConfig.versionCode())

        validate_btn.setOnClickListener {
            presenter.goToNextStep()
        }
    }
}
