package com.ysdc.comet.navigation

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.ysdc.comet.common.navigation.NavigationManager

class NavigationManagerImpl() : NavigationManager {

    private fun hideKeyboard(fromActivity: AppCompatActivity) {
        val view = fromActivity.currentFocus
        if (view != null) {
            val imm = fromActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}