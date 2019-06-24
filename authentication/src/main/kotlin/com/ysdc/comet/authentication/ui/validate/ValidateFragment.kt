package com.ysdc.comet.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.ysdc.comet.common.ui.base.BaseFragment
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import kotlinx.android.synthetic.main.fragment_validate.*
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
        }
        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun setUp(view: View) {

        validate_btn.setOnClickListener {
            hideKeyboard()
            presenter.validateCode(phone_code_field.text.toString())
        }

        phone_code_field.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                presenter.validateCode(phone_code_field.text.toString())
                true
            } else {
                false
            }
        }
    }


    companion object {
        private const val EXTRA_TAB = "EXTRA_TAB"

        fun newInstance(): ValidateFragment {
            return ValidateFragment()
        }
    }
}