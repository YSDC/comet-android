package com.ysdc.comet.common.ui.base

import com.ysdc.comet.common.R
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.exception.*
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import com.ysdc.comet.common.utils.AppConstants.LOGGING_CATEGORY_ACTIVITY_LIFECYCLE
import com.ysdc.comet.common.utils.AppConstants.LOGGING_CATEGORY_INTERACTIONS
import com.ysdc.comet.common.utils.CrashlyticsUtils
import com.ysdc.comet.common.utils.NetworkUtils
import com.ysdc.comet.common.ui.utils.DialogBuilder
import com.ysdc.comet.model.Animation
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

private const val STATE_CURRENT_TAB_ID = "STATE_CURRENT_TAB_ID"
private const val STATE_BACK_STACK_MANAGER = "STATE_BACK_STACK_MANAGER"

abstract class BaseActivity : AppCompatActivity(), MvpView, BaseFragment.Callback {

    private var versionDialog: AlertDialog? = null

    @Inject
    protected lateinit var crashlyticsUtils: CrashlyticsUtils
    @Inject
    protected lateinit var generalConfig: GeneralConfig
    @Inject
    protected lateinit var networkUtils: NetworkUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun onStart() {
        super.onStart()
        crashlyticsUtils.log(LOGGING_CATEGORY_ACTIVITY_LIFECYCLE, "onStart", this.javaClass.simpleName, 0)
    }

    override fun onStop() {
        crashlyticsUtils.log(LOGGING_CATEGORY_ACTIVITY_LIFECYCLE, "onStop", this.javaClass.simpleName, 0)
        if (versionDialog != null) {
            versionDialog!!.dismiss()
        }
        super.onStop()
    }

    override fun onRestart() {
        crashlyticsUtils.log(LOGGING_CATEGORY_ACTIVITY_LIFECYCLE, "onRestart", this.javaClass.simpleName, 0)
        super.onRestart()
    }

    override fun onDestroy() {
        crashlyticsUtils.log(LOGGING_CATEGORY_ACTIVITY_LIFECYCLE, "onDestroy", this.javaClass.simpleName, 0)
        super.onDestroy()
    }

    override fun onError(throwable: Throwable) {
        crashlyticsUtils.logException(throwable)
        Timber.e(throwable, "An Error occurred")
        if (throwable is NoConnectivityException) {
            onError(getString(R.string.exception_no_connectivity))
        } else {
            onError(getString(R.string.exception_undefined))
        }
    }

    override fun onError(@StringRes resId: Int) {
        onError(getString(resId))
    }


    override fun onError(message: String) {
        Timber.e("ERROR: %s", message)
        showMessage(message)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

    override fun isNetworkConnected(): Boolean {
        return networkUtils.isNetworkConnected(applicationContext)
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun displaySystemView() {
        val uri = Uri.fromParts("package", application.packageName, null)
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = uri
        startActivity(intent)
    }

    override fun showVersionDialog(title: String, content: String, cancelable: Boolean) {
        if (!isDestroyed) {
            val dialogBuilder = DialogBuilder()
            versionDialog =
                dialogBuilder.displayVersionDialog(this, title, content, cancelable, generalConfig.storeUrl())
        }
    }

    override fun onVersionDialogClosed() {

    }

    override fun restartApp() {
        val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(i)
        finish()
        Runtime.getRuntime().exit(0)
    }

    override fun provideResources(): Resources {
        return resources
    }

    override fun onFragmentAttached() {
        //Nothing to do
    }

    override fun onFragmentDetached(tag: String) {
        //Nothing to do
    }
}
