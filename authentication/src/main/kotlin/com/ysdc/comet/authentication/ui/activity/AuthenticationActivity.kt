package com.ysdc.comet.authentication.ui.activity

import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.ysdc.comet.authentication.R
import com.ysdc.comet.authentication.ValidateFragment
import com.ysdc.comet.authentication.ui.register.RegisterFragment
import com.ysdc.comet.authentication.ui.team.TeamFragment
import com.ysdc.comet.common.ui.base.BaseActivity
import com.ysdc.comet.common.ui.utils.FragmentAdapter
import kotlinx.android.synthetic.main.activity_authentication.*
import javax.inject.Inject


/**
 * Created by david on 1/25/18.
 */

class AuthenticationActivity : BaseActivity(), AuthenticationMvpView {

    private lateinit var adapter: FragmentAdapter
    @Inject
    internal lateinit var presenter: AuthenticationMvpPresenter<AuthenticationMvpView>

    private var alertDialog: LottieAlertDialog? = null

    private val teamFragment = TeamFragment.newInstance()
    private val registerFragment = RegisterFragment.newInstance()
    private val validateFragment = ValidateFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        presenter.onAttach(this)
        initView()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun initialize() {
        Timber.d { "Authentication manager start initialisation" }
    }

    override fun initialisationDone() {
        Timber.d { "Authentication manager initialized" }
    }

    override fun verificationSucceed() {
        Timber.d { "verificationSucceed" }
    }

    override fun codeSent() {
        authenticationContainer.currentItem = 2
    }

    override fun authenticationDone() {
        Timber.d { "authenticated" }
        //TODO move to home activity
    }

    fun onTeamValidated(){
        authenticationContainer.currentItem = 1
    }

    override fun onVerificationCodeError() {
        alertDialog = LottieAlertDialog.Builder(this, DialogTypes.TYPE_ERROR)
            .setTitle("Error")
            .setDescription(getString(R.string.error_wrong_code))
            .setPositiveText(getString(R.string.action_check_phone))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    authenticationContainer.currentItem = 1
                }
            })
            .setNegativeText(getString(R.string.action_resend))
            .setNegativeListener(object : ClickListener{
                override fun onClick(dialog: LottieAlertDialog) {
                    registerFragment.presenter.startAuthentication()
                }
            })
            .build()
        alertDialog!!.show()
    }

    override fun onBackPressed() {
        if (authenticationContainer.currentItem > 0) {
            authenticationContainer.currentItem = authenticationContainer.currentItem - 1
        }
        //super.onBackPressed()
    }

    private fun initView() {
        presenter.initAuthenticationManager(this)

        adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragments(teamFragment)
        adapter.addFragments(registerFragment)
        adapter.addFragments(validateFragment)

        authenticationContainer.adapter = adapter
        authenticationContainer.offscreenPageLimit = adapter.count
        authenticationContainer.setPagingEnabled(false)
    }
}
