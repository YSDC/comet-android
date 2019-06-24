package com.ysdc.comet.authentication.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.ysdc.comet.authentication.R
import com.ysdc.comet.common.ui.base.BaseFragment
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import kotlinx.android.synthetic.main.fragment_register.*
import timber.log.Timber
import javax.inject.Inject

class RegisterFragment : BaseFragment(), RegisterMvpView {

    override val customTitle: String = EMPTY_STRING
    override val screenName: String = "todo"
    override val isActionBarVisible: Boolean = false

    private var alertDialog: LottieAlertDialog? = null

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
        alertDialog?.let { it.dismiss() }
        super.onDestroyView()
    }

    override fun setUp(view: View) {
        val roleAdapter = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item, presenter.getRoles())
        role_spinner.adapter = roleAdapter
        role_spinner.setSelection(presenter.getIndexRoleSelected())
        role_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, index: Int, l: Long) {
                presenter.setRoleSelected(index)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                Timber.d("Role: nothing selected")
            }
        }

        validate_btn.setOnClickListener {
            if (areFieldsValid()) {
                presenter.startAuthentication()
            }
        }
    }

    private fun areFieldsValid(): Boolean {
        var hasErrors = false

        if (!presenter.isFirstNameValid(register_firstName_content.text.toString())) {
            register_firstName_layout.error = getString(R.string.error_wrong_firstname)
            hasErrors = true
        } else {
            register_firstName_layout.error = null
        }
        if (!presenter.isLastNameValid(register_lastname_content.text.toString())) {
            register_lastname_layout.error = getString(R.string.error_wrong_lastname)
            hasErrors = true
        } else {
            register_lastname_layout.error = null
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
        if (!presenter.isRoleValid(presenter.getRoleSelected())) {
            showMissingRole()
            hasErrors = true
        } else {
            register_email_layout.error = null
        }

        return hasErrors
    }

    private fun showMissingRole() {
        alertDialog = LottieAlertDialog.Builder(baseActivity, DialogTypes.TYPE_ERROR)
            .setTitle("Error")
            .setDescription(getString(R.string.error_missing_role))
            .setPositiveText(getString(R.string.action_ok))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    dialog.dismiss()
                }
            })
            .build()
        alertDialog!!.show()
    }

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }
}