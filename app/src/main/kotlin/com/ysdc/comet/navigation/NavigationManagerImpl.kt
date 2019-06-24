package com.ysdc.comet.navigation

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.ysdc.comet.authentication.ui.activity.AuthenticationActivity
import com.ysdc.comet.common.navigation.NavigationManager
import com.ysdc.comet.ui.main.MainActivity

class NavigationManagerImpl() : NavigationManager {

    override fun displayAuthenticationView(fromActivity: Activity) {
        fromActivity.startActivity(AuthenticationActivity.newInstance(fromActivity))
        fromActivity.finish()
    }

    override fun displayMainView(fromActivity: Activity) {
        fromActivity.startActivity(MainActivity.newInstance(fromActivity))
        fromActivity.finish()
    }

    private fun hideKeyboard(fromActivity: AppCompatActivity) {
        val view = fromActivity.currentFocus
        if (view != null) {
            val imm = fromActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}