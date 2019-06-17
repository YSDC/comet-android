package com.ysdc.comet.common.navigation

import ae.propertyfinder.model.Property
import ae.propertyfinder.model.Screen
import ae.propertyfinder.model.SearchFilters
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.reactivex.Observable

interface NavigationManager {
    fun displayLoginEmail(fromActivity: AppCompatActivity, fromFragment: Fragment?)
    fun displayLoginGoogle(fromActivity: AppCompatActivity, fromFragment: Fragment?, intent: Intent)
    fun displayResetPassword(fromActivity: AppCompatActivity)
    fun updateSearchContainer(fragment : Fragment)
    fun notifySearchListDisplayed()
    fun notifySearchMapDisplayed()
    fun updateSearchList(searchFilters: SearchFilters)
    fun updateSearchMap(searchFilters: SearchFilters)
    fun navigateToSearchList(searchFilters: SearchFilters)
    fun navigateToMap(searchFilters: SearchFilters)
    fun displayPropertyDetails(fromActivity: AppCompatActivity, property: Property)
    fun displayLocationSelection(fromActivity: AppCompatActivity)
    fun getLastScreen() : Screen
    fun displayCategory(fromActivity: AppCompatActivity)
    fun displayFilters(fromActivity: AppCompatActivity)
    fun displayCluster(fromActivity: AppCompatActivity, searchFilters: SearchFilters)
    fun pressBack(fromActivity: AppCompatActivity)
    fun getSearchList() : Fragment
    fun getSearchMap() : Fragment
}