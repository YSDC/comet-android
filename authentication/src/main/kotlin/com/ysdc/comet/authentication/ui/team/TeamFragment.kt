package com.ysdc.comet.authentication.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.github.ajalt.timberkt.Timber
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.ysdc.comet.authentication.R
import com.ysdc.comet.authentication.ui.activity.AuthenticationActivity
import com.ysdc.comet.common.ui.base.BaseFragment
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import kotlinx.android.synthetic.main.fragment_team.*
import javax.inject.Inject


class TeamFragment : BaseFragment(), TeamMvpView {

    override val customTitle: String = EMPTY_STRING
    override val screenName: String = "todo"
    override val isActionBarVisible: Boolean = false

    @Inject
    lateinit var presenter: TeamMvpPresenter<TeamMvpView>

    override fun shouldToolbarBeElevated(): Boolean {
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)

        presenter.onAttach(this)
        if (baseActivity != null) {
            baseActivity!!.supportActionBar?.title = customTitle
        }
        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
    override fun setUp(view: View) {
        validate_btn.setOnClickListener {
            presenter.validateTeamCode(team_field.text.toString())
        }
        team_field.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.validateTeamCode(v.text.toString())
                true
            } else{
                false
            }
        }
    }

    override fun displayError(messageId : Int){
        val builder = LottieAlertDialog.Builder(baseActivity, DialogTypes.TYPE_ERROR)
            .setTitle(getString(R.string.error))
            .setDescription(getString(messageId))
            .setPositiveText(getString(R.string.action_ok))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    dialog.dismiss()
                }
            })
        if( baseActivity?.alertDialog != null){
            baseActivity?.alertDialog!!.changeDialog(builder)
        }else{
            baseActivity?.alertDialog = builder.build()
            baseActivity?.alertDialog!!.show()
        }
    }

    override fun teamValidated() {
        hideAlert()
        if (baseActivity is AuthenticationActivity) {
            (baseActivity as AuthenticationActivity).onTeamValidated()
        } else {
            Timber.e { "OUPS, team fragment activity is wrong, impossible!" }
        }
    }

    companion object {
        fun newInstance(): TeamFragment {
            return TeamFragment()
        }
    }
}