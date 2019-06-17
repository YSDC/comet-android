/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.ysdc.comet.common.ui.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.ysdc.comet.common.R
import com.ysdc.comet.common.utils.AppConstants
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


abstract class BaseFragment : Fragment(), MvpView {

    protected var baseActivity: BaseActivity? = null

    @Inject
    protected lateinit var crashlyticsUtils: com.ysdc.comet.common.utils.CrashlyticsUtils

    protected val backArrow: Int
        get() = R.drawable.ic_arrow_back_black

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        //TODO: remove this when old app is not use anymore
        val actionBar = baseActivity?.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(shouldDisplayBackButton())
            if (shouldDisplayBackButton()) {
                actionBar.setHomeAsUpIndicator(backArrow)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun onStart() {
        crashlyticsUtils.log(AppConstants.LOGGING_CATEGORY_FRAGMENT_LIFECYCLE, "onStart", javaClass.simpleName, 0)
        super.onStart()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        colorStatusBar(R.color.colorPrimaryDark)
    }

    override fun onStop() {
        crashlyticsUtils.log(AppConstants.LOGGING_CATEGORY_FRAGMENT_LIFECYCLE, "onStop", javaClass.simpleName, 0)
        super.onStop()
    }

    override fun onDestroy() {
        crashlyticsUtils.log(AppConstants.LOGGING_CATEGORY_FRAGMENT_LIFECYCLE, "onDestroy", javaClass.simpleName, 0)
        super.onDestroy()
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
        if (baseActivity != null) {
            baseActivity!!.onError(throwable)
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
        baseActivity!!.hideKeyboard()
    }

    fun showKeyboard(view: View) {
        view.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    protected fun colorStatusBar(@ColorRes color: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP &&
            activity != null &&
            (activity as FragmentActivity).window != null
        ) {
            val window = (activity as FragmentActivity).window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor((activity as FragmentActivity), color)
        }
    }

    protected fun shouldDisplayBackButton(): Boolean {
        return false
    }

    override fun displaySystemView() {
        baseActivity!!.displaySystemView()
    }

    override fun restartApp() {
        baseActivity!!.restartApp()
    }

    override fun showVersionDialog(title: String, content: String, cancelable: Boolean) {
        //TODO
    }

    override fun onVersionDialogClosed() {
        //TODO
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

    abstract fun shouldToolbarBeElevated(): Boolean

    abstract val screenName: String

    abstract val customTitle: String

    abstract val isActionBarVisible: Boolean

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    override fun onBackPressed() {
        //Nothing to do
    }
}
