package com.ysdc.comet.authentication.ui.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.ajalt.timberkt.Timber
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.ysdc.comet.authentication.R
import com.ysdc.comet.authentication.ui.activity.AuthenticationActivity
import com.ysdc.comet.common.ui.base.BaseFragment
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_team.*
import javax.inject.Inject


class TeamFragment : BaseFragment(), TeamMvpView {

    override val customTitle: String = EMPTY_STRING
    override val screenName: String = "todo"
    override val isActionBarVisible: Boolean = false

    @Inject
    lateinit var presenter: TeamMvpPresenter<TeamMvpView>

    private lateinit var compositeDisposable: CompositeDisposable

    override fun shouldToolbarBeElevated(): Boolean {
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)

        presenter.onAttach(this)
        if (baseActivity != null) {
            baseActivity!!.supportActionBar?.title = customTitle
        }

        compositeDisposable = CompositeDisposable()
        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        compositeDisposable.dispose()
        super.onDestroyView()
    }

    override fun setUp(view: View) {

        compositeDisposable.add(
            presenter.loadTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { displayLoading(R.string.loading_teams) }
                .doOnSuccess { hideAlert() }
                .subscribe({ teams ->
                    val teamsAdapter = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item, teams)
                    team_selection_spinner.adapter = teamsAdapter
                }, { throwable -> onError(throwable) })
        )

        validate_btn.setOnClickListener {
            hideKeyboard()
            presenter.validateTeamCode(team_field.text.toString())
        }
        team_field.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                presenter.validateTeamCode(v.text.toString())
                true
            } else {
                false
            }
        }

        team_selection_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, index: Int, l: Long) {
                presenter.setTeamSelected(index)
                validate_btn.visibility = View.VISIBLE
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                timber.log.Timber.d("Role: nothing selected")
            }
        }
    }

    override fun displayError(messageId: Int) {
        val builder = LottieAlertDialog.Builder(baseActivity, DialogTypes.TYPE_ERROR)
            .setTitle(getString(R.string.error))
            .setDescription(getString(messageId))
            .setPositiveText(getString(R.string.action_ok))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    dialog.dismiss()
                }
            })
        if (baseActivity?.alertDialog != null) {
            baseActivity?.alertDialog!!.changeDialog(builder)
        } else {
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