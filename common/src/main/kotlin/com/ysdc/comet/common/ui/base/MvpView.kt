
package com.ysdc.comet.common.ui.base

import android.content.res.Resources
import androidx.annotation.StringRes

interface MvpView {

    fun isNetworkConnected(): Boolean

    fun onError(throwable: Throwable)

    fun onError(@StringRes resId: Int)

    fun onError(message: String)

    fun showMessage(message: String)

    fun showMessage(@StringRes resId: Int)

    fun hideKeyboard()

    fun displaySystemView()

    fun restartApp()

    fun showVersionDialog(title: String, content: String, cancelable: Boolean)

    fun onVersionDialogClosed()

    fun provideResources(): Resources

    fun onBackPressed()

    fun hideAlert()

    fun displayLoading(messageId : Int)
}
