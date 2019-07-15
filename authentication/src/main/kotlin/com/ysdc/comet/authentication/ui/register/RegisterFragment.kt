package com.ysdc.comet.authentication.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ysdc.comet.authentication.R
import com.ysdc.comet.common.ui.base.BaseFragment
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import com.ysdc.comet.model.UserRole
import kotlinx.android.synthetic.main.fragment_register.*
import timber.log.Timber
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
        baseActivity?.alertDialog?.dismiss()
        super.onDestroyView()
    }

    override fun setUp(view: View) {
        val roleAdapter = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item, presenter.getRoles())
        role_spinner.adapter = roleAdapter
        role_spinner.setSelection(presenter.getUser().role.ordinal)
        role_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, index: Int, l: Long) {
                presenter.getUser().role = UserRole.values()[index]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                Timber.d("Role: nothing selected")
            }
        }

        validate_btn.setOnClickListener {
            if (areFieldsValid()) {
                storeFields()
                presenter.startAuthentication()
            }
        }

        register_email_content.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            } else {
                false
            }
        }

        register_firstName_content.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                register_firstName_layout.hint = getString(R.string.register_firstName)
            } else {
                register_firstName_layout.hint = getString(R.string.register_firstName_placeholder)
            }
        }

        register_lastName_content.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                register_lastName_layout.hint = getString(R.string.register_lastName)
            } else {
                register_lastName_layout.hint = getString(R.string.register_lastName_placeholder)
            }
        }

        register_phone_content.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                register_phone_layout.hint = getString(R.string.register_phone)
            } else {
                register_phone_layout.hint = getString(R.string.register_phone_placeholder)
            }
        }

        register_email_content.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                register_email_layout.hint = getString(R.string.register_email)
            } else {
                register_email_layout.hint = getString(R.string.register_email_placeholder)
            }
        }

        fillFields()
    }

    private fun fillFields() {
        register_firstName_content.setText(presenter.getUser().firstName)
        register_lastName_content.setText(presenter.getUser().lastName)
        register_phone_content.setText(presenter.getUser().phone)
        register_email_content.setText(presenter.getUser().email)
    }

    private fun storeFields() {
        presenter.getUser().firstName = register_firstName_content.text.toString()
        presenter.getUser().lastName = register_lastName_content.text.toString()
        presenter.getUser().phone = register_phone_content.text.toString()
        presenter.getUser().email = register_email_content.text.toString()
        presenter.updateUser()
    }

    private fun areFieldsValid(): Boolean {
        var hasErrors = false

        if (!presenter.isFirstNameValid(register_firstName_content.text.toString())) {
            register_firstName_layout.error = getString(R.string.error_wrong_firstname)
            hasErrors = true
        } else {
            register_firstName_layout.error = null
        }
        if (!presenter.isLastNameValid(register_lastName_content.text.toString())) {
            register_lastName_layout.error = getString(R.string.error_wrong_lastname)
            hasErrors = true
        } else {
            register_lastName_layout.error = null
        }
        if (!presenter.isPhoneValid(register_phone_content.text.toString())) {
            register_phone_layout.error = getString(R.string.error_wrong_phone)
            hasErrors = true
        } else {
            register_phone_layout.error = null
        }
        if (!presenter.isEmailValid(register_email_content.text.toString())) {
            register_email_layout.error = getString(R.string.error_wrong_email)
            hasErrors = true
        } else {
            register_email_layout.error = null
        }

        return !hasErrors
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}