package com.ysdc.comet.authentication.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ajalt.timberkt.Timber
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
    }

    override fun teamValidated() {
        if(baseActivity is AuthenticationActivity){
            (baseActivity as AuthenticationActivity).onTeamValidated()
        }else{
            Timber.e{"OUPS, team fragment activity is wrong, impossible!"}
        }
    }

    companion object {
        fun newInstance(): TeamFragment {
            return TeamFragment()
        }
    }
}