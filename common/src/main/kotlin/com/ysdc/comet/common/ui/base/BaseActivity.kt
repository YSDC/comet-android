package com.ysdc.comet.common.ui.base

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.ysdc.comet.common.R
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.exception.NoConnectivityException
import com.ysdc.comet.common.exception.ValidationException
import com.ysdc.comet.common.utils.CrashlyticsUtils
import com.ysdc.comet.common.utils.NetworkUtils
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), MvpView, BaseFragment.Callback {

    var alertDialog: LottieAlertDialog? = null

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

    override fun onStop() {
        if (alertDialog != null) {
            alertDialog!!.dismiss()
        }
        super.onStop()
    }

    override fun onError(throwable: Throwable) {
        crashlyticsUtils.logException(throwable)
        Timber.e(throwable, "An Error occurred")
        when (throwable) {
            is NoConnectivityException -> onError(getString(R.string.exception_no_connectivity))
            is ValidationException -> onError(throwable.message ?: getString(R.string.exception_validation))
            else -> onError(getString(R.string.exception_undefined))
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

        alertDialog = LottieAlertDialog.Builder(this, DialogTypes.TYPE_ERROR)
            .setTitle(getString(R.string.error))
            .setDescription(message)
            .setPositiveText(getString(R.string.action_ok))
            .setPositiveListener(object : ClickListener {
                override fun onClick(dialog: LottieAlertDialog) {
                    dialog.dismiss()
                }
            })
            .build()
        alertDialog!!.show()
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
            val builder = LottieAlertDialog.Builder(this, DialogTypes.TYPE_WARNING)
                .setTitle(title)
                .setDescription(content)
                .setPositiveText(getString(R.string.action_download))
                .setPositiveListener(object : ClickListener {
                    override fun onClick(dialog: LottieAlertDialog) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(generalConfig.storeUrl())))
                        dialog.dismiss()
                    }
                })
            if (cancelable) {
                builder.setNegativeText(getString(R.string.action_ok))
                    .setNegativeListener(object : ClickListener {
                        override fun onClick(dialog: LottieAlertDialog) {
                            onVersionDialogClosed()
                            dialog.dismiss()
                        }
                    })
            }
            alertDialog = builder.build()
            alertDialog!!.setCancelable(cancelable)
            alertDialog!!.show()
        }
    }

    override fun displayLoading(messageId : Int){
        hideAlert()
        alertDialog = LottieAlertDialog.Builder(this, DialogTypes.TYPE_LOADING)
            .setTitle(getString(R.string.action_loading))
            .setDescription(getString(messageId))
            .build()
        alertDialog!!.setCancelable(false)
        alertDialog!!.show()
    }

    override fun hideAlert() {
        if(alertDialog != null){
            alertDialog!!.dismiss()
            alertDialog = null
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
