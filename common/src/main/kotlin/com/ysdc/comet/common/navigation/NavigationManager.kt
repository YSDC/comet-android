package com.ysdc.comet.common.navigation

import android.app.Activity

interface NavigationManager {
    fun displayMainView(fromActivity: Activity)
    fun displayAuthenticationView(fromActivity: Activity)
}