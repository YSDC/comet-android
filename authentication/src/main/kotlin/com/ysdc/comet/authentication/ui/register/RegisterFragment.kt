package com.ysdc.comet.authentication.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysdc.comet.authentication.R
import com.ysdc.comet.common.ui.base.BaseFragment
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import kotlinx.android.synthetic.main.fragment_register.*
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

    private fun fieldsValid() : Boolean{
        var hasErrors = false

        if(!presenter.isFirstNameValid(register_firstName_content.text.toString())){
            register_firstName_layout.error = getString(R.string.error_firstname)
            hasErrors = true
        }else{
            register_firstName_layout.error = null
        }
        if(!presenter.isLastNameValid(register_lastname_content.text.toString())){
            register_lastname_layout.error = getString(R.string.error_lastname)
            hasErrors = true
        }else{
            register_lastname_layout.error = null
        }
        if(!presenter.isPhoneValid(register_phone_content.text.toString())){
            register_phone_layout.error = getString(R.string.error_phone)
            hasErrors = true
        }else{
            register_phone_layout.error = null
        }
        if(!presenter.isEmailValid(register_email_content.text.toString())){
            register_email_layout.error = getString(R.string.error_email)
            hasErrors = true
        }else{
            register_email_layout.error = null
        }
        if(!presenter.isRoleValid(register_email_content.text.toString())){
            register_email_layout.error = getString(R.string.error_email)
            hasErrors = true
        }else{
            register_email_layout.error = null
        }

        return hasErrors
    }
    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}