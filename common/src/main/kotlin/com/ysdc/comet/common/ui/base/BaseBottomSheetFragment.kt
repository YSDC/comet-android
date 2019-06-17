package com.ysdc.comet.common.ui.base

import com.ysdc.comet.common.R
import com.ysdc.comet.common.exception.NoConnectivityException
import com.ysdc.comet.common.utils.AppConstants
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by david on 28/3/18.
 */

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment(), MvpView {

    private var baseActivity: BaseActivity? = null

    @Inject
    protected lateinit var crashlyticsUtils: com.ysdc.comet.common.utils.CrashlyticsUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is BaseActivity) {
            this.baseActivity = context
            context.onFragmentAttached()
        }
    }

    override fun onError(throwable: Throwable) {
        if (throwable is NoConnectivityException) {
            onError(getString(R.string.exception_no_connectivity))
        } else {
            onError(getString(R.string.exception_undefined))
        }
    }

    override fun onError(message: String) {
        if (baseActivity != null) {
            baseActivity!!.onError(message)
        }
    }

    override fun onError(@StringRes resId: Int) {
        if (baseActivity != null) {
            baseActivity!!.onError(resId)
        }
    }

    override fun showMessage(message: String) {
        if (baseActivity != null) {
            baseActivity!!.showMessage(message)
        }
    }

    override fun showMessage(@StringRes resId: Int) {
        if (baseActivity != null) {
            baseActivity!!.showMessage(resId)
        }
    }

    override fun isNetworkConnected(): Boolean {
        return if (baseActivity != null) {
            baseActivity!!.isNetworkConnected()
        } else false
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    override fun displaySystemView() {
        baseActivity!!.displaySystemView()
    }

    override fun showVersionDialog(title: String, content: String, cancelable: Boolean) {
        //TODO
    }

    override fun onVersionDialogClosed() {
        //TODO
    }

    override fun restartApp() {
        baseActivity!!.restartApp()
    }

    override fun provideResources(): Resources {
        return resources
    }

    protected fun logInteractionOnCrashlytics(v: View) {
        var viewText = AppConstants.EMPTY_STRING
        try {
            when (v) {
                is Button -> viewText = v.text.toString()
                is TextView -> viewText = v.text.toString()
                is ImageButton -> viewText = v.tag.toString()
            }
        } catch (e: Exception) {
            crashlyticsUtils.logException(e)
        }

        crashlyticsUtils.log(AppConstants.LOGGING_CATEGORY_INTERACTIONS, "click", viewText)
    }

    protected abstract fun setUp(view: View)
}