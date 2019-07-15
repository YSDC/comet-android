package com.ysdc.comet.authentication.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.ysdc.comet.authentication.R
import com.ysdc.comet.authentication.ValidateFragment
import com.ysdc.comet.authentication.ui.register.RegisterFragment
import com.ysdc.comet.authentication.ui.team.TeamFragment
import com.ysdc.comet.common.navigation.NavigationManager
import com.ysdc.comet.common.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_authentication.*
import javax.inject.Inject


/**
 * Created by david on 1/25/18.
 */

class AuthenticationActivity : BaseActivity(), AuthenticationMvpView {

    @Inject
    internal lateinit var presenter: AuthenticationMvpPresenter<AuthenticationMvpView>
    @Inject
    internal lateinit var navigationManager: NavigationManager

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
        hideAlert()
        showValidation()
    }

    override fun authenticationDone() {
        Timber.d { "authenticated" }
        navigationManager.displayMainView(this)
    }

    fun onTeamValidated() {
        showRegister()
    }

    override fun onVerificationCodeError() {
        alertDialog = LottieAlertDialog.Builder(this, DialogTypes.TYPE_ERROR)
            .setTitle(getString(R.string.error))
            .setDescription(getString(R.string.error_wrong_code))
            .setPositiveText(getString(R.string.action_check_phone))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    supportFragmentManager.popBackStack()
                }
            })
            .setNegativeText(getString(R.string.action_resend))
            .setNegativeListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    displayLoading(R.string.phone_code_request)
                    presenter.resendCode()
                }
            })
            .build()
        alertDialog!!.show()
    }

//    override fun onBackPressed() {
//        if (authenticationContainer.currentItem > 0) {
//            authenticationContainer.currentItem = authenticationContainer.currentItem - 1
//        }
//        //super.onBackPressed()
//    }

    private fun initView() {
        presenter.initAuthenticationManager(this)
        if (presenter.hasTeamCode()) {
            showRegister()
        } else {
            showTeamSelection()
        }
    }

    private fun showTeamSelection() {
        val teamFragment = TeamFragment.newInstance()
        val tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.authenticationContainer, teamFragment, teamFragment.javaClass.name)
        tr.addToBackStack(null)
        tr.commit()
    }

    private fun showValidation() {
        val validateFragment = ValidateFragment.newInstance()
        val tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.authenticationContainer, validateFragment, validateFragment.javaClass.name)
        tr.addToBackStack(null)
        tr.commit()
    }

    private fun showRegister() {
        val registerFragment = RegisterFragment.newInstance()
        val tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.authenticationContainer, registerFragment, registerFragment.javaClass.name)
        tr.addToBackStack(null)
        tr.commit()
    }

    companion object {
        fun newInstance(context: Context): Intent {
            val intent = Intent(context, AuthenticationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            return intent
        }
    }
}
