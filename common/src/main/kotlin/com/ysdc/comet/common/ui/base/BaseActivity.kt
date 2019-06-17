package com.ysdc.comet.common.ui.base

import com.ysdc.comet.common.R
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.exception.*
import com.ysdc.comet.common.ui.UiConstants.TAB_DEFAULT
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import com.ysdc.comet.common.utils.AppConstants.LOGGING_CATEGORY_ACTIVITY_LIFECYCLE
import com.ysdc.comet.common.utils.AppConstants.LOGGING_CATEGORY_INTERACTIONS
import com.ysdc.comet.common.utils.CrashlyticsUtils
import com.ysdc.comet.common.utils.NetworkUtils
import com.ysdc.comet.common.ui.utils.DialogBuilder
import ae.propertyfinder.model.Animation
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
import com.jetradar.multibackstack.BackStackEntry
import com.jetradar.multibackstack.BackStackManager
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

private const val STATE_CURRENT_TAB_ID = "STATE_CURRENT_TAB_ID"
private const val STATE_BACK_STACK_MANAGER = "STATE_BACK_STACK_MANAGER"

abstract class BaseActivity : AppCompatActivity(), MvpView, BaseFragment.Callback {

    var curTabId: Int = TAB_DEFAULT
        protected set
    var currentMenu: Int = R.menu.menu_empty
        private set
    private var versionDialog: AlertDialog? = null
    private var backStackManager: BackStackManager? = null

    @Inject
    protected lateinit var crashlyticsUtils: CrashlyticsUtils
    @Inject
    protected lateinit var generalConfig: GeneralConfig
    @Inject
    protected lateinit var networkUtils: NetworkUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        backStackManager = BackStackManager()
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
        backStackManager = null
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_CURRENT_TAB_ID, curTabId)
        outState.putParcelable(STATE_BACK_STACK_MANAGER, backStackManager!!.saveState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        curTabId = savedInstanceState.getInt(STATE_CURRENT_TAB_ID)
        backStackManager!!.restoreState(savedInstanceState.getParcelable(STATE_BACK_STACK_MANAGER))
    }

    override fun onError(throwable: Throwable) {
        crashlyticsUtils.logException(throwable)
        Timber.e(throwable, "An Error occurred")
        if (throwable is NoConnectivityException) {
            onError(getString(R.string.exception_no_connectivity))
        } else if (throwable is BackendException || throwable is ValidationException) {
            onError(throwable.message ?: throwable.javaClass.name)
        } else if (throwable is GoogleException) {
            onError(getString(R.string.error_google_connection))
        } else if (throwable is NotLoggedException) {
            onError(getString(R.string.exception_not_logged))
        } else if (throwable is InvalidCredentialsException) {
            //TODO
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

    /**
     * @return false if failed to put fragment in back stack. Relates to issue:
     * java.lang.IllegalStateException: Fragment is not currently in the FragmentManager at
     * android.support.v4.app.FragmentManagerImpl.saveFragmentInstanceState(FragmentManager.java:702)
     */
    protected fun pushFragmentToBackStack(hostId: Int, fragment: Fragment): Boolean {
        try {
            val entry = BackStackEntry.create(supportFragmentManager, fragment)
            backStackManager!!.push(hostId, entry)
            return true
        } catch (e: Exception) {
            Timber.e(e, "Failed to add fragment to back stack")
            return false
        }

    }

    protected fun popFragmentFromBackStack(hostId: Int): Fragment? {
        val entry = backStackManager!!.pop(hostId)
        return if (entry != null) entry.toFragment(this) else null
    }

    protected fun popFragmentFromBackStack(): Pair<Int, Fragment>? {
        val pair = backStackManager!!.pop()
        return if (pair != null) Pair.create(pair.first, pair.second!!.toFragment(this)) else null
    }

    /**
     * @return false if back stack is missing.
     */
    protected fun resetBackStackToRoot(hostId: Int): Boolean {
        return backStackManager!!.resetToRoot(hostId)
    }

    /**
     * @return false if back stack is missing.
     */
    protected fun clearBackStack(hostId: Int): Boolean {
        return backStackManager!!.clear(hostId)
    }

    /**
     * @return the number of fragments in back stack.
     */
    protected fun backStackSize(hostId: Int): Int {
        return backStackManager!!.backStackSize(hostId)
    }

    open fun showFragment(fragment: BaseFragment, addToBackStack: Boolean = true, animation : Animation?) {
        //Implemented by class that inherit, if they need it
    }

    protected fun updateCurrentMenu(fragment: BaseFragment) {
        this.currentMenu = fragment.menu
    }

    protected fun logInteractionOnCrashlytics(v: View) {
        var viewText = EMPTY_STRING
        try {
            if (v is Button) {
                viewText = v.text.toString()
            } else if (v is TextView) {
                viewText = v.text.toString()
            }
        } catch (e: Exception) {
            crashlyticsUtils.logException(e)
        }

        crashlyticsUtils.log(LOGGING_CATEGORY_INTERACTIONS, "click", viewText)
    }

    override fun onFragmentAttached() {
        //Nothing to do
    }

    override fun onFragmentDetached(tag: String) {
        //Nothing to do
    }
}
