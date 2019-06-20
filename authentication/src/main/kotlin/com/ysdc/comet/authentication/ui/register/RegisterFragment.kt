package com.ysdc.comet.authentication.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysdc.comet.authentication.R
import com.ysdc.comet.common.ui.base.BaseFragment
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import javax.inject.Inject

class RegisterFragment : BaseFragment(), RegisterMvpView {

    override val customTitle: String = EMPTY_STRING
    override val screenName: String = "todo"
    override val isActionBarVisible: Boolean = false

    @Inject
    lateinit var presenter: RegisterMvpPresenter<RegisterMvpView>

    override fun shouldToolbarBeElevated(): Boolean {
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        presenter.onAttach(this)
        baseActivity!!.supportActionBar?.title = customTitle
        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun setUp(view: View) {

    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}