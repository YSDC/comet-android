package com.ysdc.comet.authentication

import ae.propertyfinder.common.ui.base.BaseFragment
import ae.propertyfinder.common.utils.AppConstants.EMPTY_STRING
import android.os.Bundle
import ae.propertyfinder.authentication.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject

class ValidateFragment : BaseFragment(), ValidateMvpView {

    override val customTitle: String = EMPTY_STRING
    override val screenName: String = "todo"
    override val isActionBarVisible: Boolean = false

    @Inject
    lateinit var presenter: ValidateMvpPresenter<ValidateMvpView>

    override fun shouldToolbarBeElevated(): Boolean {
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_validate, container, false)

        presenter.onAttach(this)
        if (baseActivity != null) {
            baseActivity!!.supportActionBar?.title = customTitle
        } else if (defaultActivity != null) {
            defaultActivity!!.supportActionBar?.title = customTitle
        }
        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun setUp(view: View) {

    }


    companion object {
        private const val EXTRA_TAB = "EXTRA_TAB"

        fun newInstance(): ValidateFragment {
            return ValidateFragment()
        }
    }
}