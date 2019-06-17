package ae.propertyfinder.navigation

import ae.propertyfinder.account.ui.login.LoginActivity
import ae.propertyfinder.account.ui.reset.ResetActivity
import ae.propertyfinder.analytics.AnalyticsConstants.SCREEN_SEARCH_CATEGORY
import ae.propertyfinder.analytics.AnalyticsConstants.SCREEN_SEARCH_CLUSTER
import ae.propertyfinder.analytics.AnalyticsConstants.SCREEN_SEARCH_FILTERS
import ae.propertyfinder.analytics.AnalyticsManager
import com.ysdc.comet.common.data.prefs.PrefsConstants
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.navigation.NavigationConstants.RC_EMAIL_SIGN_IN
import com.ysdc.comet.common.navigation.NavigationConstants.RC_GOOGLE_SIGN_IN
import com.ysdc.comet.common.navigation.NavigationManager
import ae.propertyfinder.map.ui.map.MapFragment
import ae.propertyfinder.model.Property
import ae.propertyfinder.model.Screen
import ae.propertyfinder.model.SearchFilters
import ae.propertyfinder.search.ClusterFragment
import ae.propertyfinder.search.ui.category.CategoryFragment
import ae.propertyfinder.search.ui.filter.FilterFragment
import ae.propertyfinder.search.ui.location.LocationFragment
import ae.propertyfinder.search.ui.search.SearchFragment
import ae.propertyfinder.search.ui.searchlist.SearchListFragment
import ae.propertyfinder.ui.main.MainActivity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Slide

class NavigationManagerImpl(private val appPreferences: PropertyFinderPreferences, private val analyticsManager: AnalyticsManager) : NavigationManager {

    private var screen: Screen = Screen.HOME

    private var searchListFragment: SearchListFragment? = null
    private var searchMapFragment: MapFragment? = null
    private var searchContainer: SearchFragment? = null

    override fun getLastScreen(): Screen {
        return screen
    }

    override fun displayLoginEmail(fromActivity: AppCompatActivity, fromFragment: Fragment?) {
        val intent = Intent(fromActivity, LoginActivity::class.java)
        if (fromFragment != null) {
            fromFragment.startActivityForResult(intent, RC_EMAIL_SIGN_IN)
        } else {
            fromActivity.startActivityForResult(intent, RC_EMAIL_SIGN_IN)
        }
        screen = Screen.LOGIN_MAIL
    }

    override fun displayLoginGoogle(fromActivity: AppCompatActivity, fromFragment: Fragment?, intent: Intent) {
        if (fromFragment != null) {
            fromFragment.startActivityForResult(intent, RC_GOOGLE_SIGN_IN)
        } else {
            fromActivity.startActivityForResult(intent, RC_GOOGLE_SIGN_IN)
        }
        screen = Screen.LOGIN_GOOGLE
    }

    override fun displayResetPassword(fromActivity: AppCompatActivity) {
        val intent = Intent(fromActivity, ResetActivity::class.java)
        fromActivity.startActivity(intent)
        screen = Screen.RESET_PWD
    }

    override fun notifySearchListDisplayed() {
        appPreferences.setBool(PrefsConstants.SHOW_LIST_FRAGMENT, true)
        screen = Screen.SEARCH_LIST
    }

    override fun notifySearchMapDisplayed() {
        appPreferences.setBool(PrefsConstants.SHOW_LIST_FRAGMENT, false)
        screen = Screen.SEARCH_MAP
    }

    override fun updateSearchList(searchFilters: SearchFilters) {
        (getSearchList() as SearchListFragment).update(searchFilters)
    }

    override fun updateSearchMap(searchFilters: SearchFilters) {
        (getSearchMap() as MapFragment).update(searchFilters)
    }

    override fun updateSearchContainer(fragment: Fragment) {
        if (fragment is SearchFragment) {
            searchContainer = fragment
        }
    }

    override fun navigateToSearchList(searchFilters: SearchFilters) {
        if (searchContainer != null) {
            searchContainer!!.showList(searchFilters)
            screen = Screen.SEARCH_LIST
        }
    }

    override fun navigateToMap(searchFilters: SearchFilters) {
        if (searchContainer != null) {
            searchContainer!!.showMap(searchFilters)
            screen = Screen.SEARCH_MAP
        }
    }

    override fun getSearchList(): Fragment {
        if (searchListFragment == null) {
            searchListFragment = SearchListFragment.newInstance()
        }
        return searchListFragment!!
    }

    override fun getSearchMap(): Fragment {
        if (searchMapFragment == null) {
            //TODO: Change when real map implementation is done
            searchMapFragment = MapFragment.newInstance()
        }
        return searchMapFragment!!
    }

    override fun displayPropertyDetails(fromActivity: AppCompatActivity, property: Property) {
        Toast.makeText(fromActivity, "property clicked", Toast.LENGTH_LONG).show()
    }

    override fun displayCategory(fromActivity: AppCompatActivity) {
        if (fromActivity is MainActivity) {
            analyticsManager.trackScreenEvent(SCREEN_SEARCH_CATEGORY)
            val fragment = CategoryFragment.newInstance()
            fragment.enterTransition = Slide(Gravity.TOP).setDuration(400)
            fragment.returnTransition = Slide(Gravity.TOP).setDuration(400)
            fromActivity.showFragment(fragment, true, null)
        }
    }

    override fun displayFilters(fromActivity: AppCompatActivity) {
        if (fromActivity is MainActivity) {
            // TODO Analytics
            analyticsManager.trackScreenEvent(SCREEN_SEARCH_FILTERS)
            val fragment = FilterFragment.newInstance()
            fragment.enterTransition = Fade(Fade.IN).setDuration(200)
            fragment.returnTransition = Fade(Fade.OUT).setDuration(200)
            fromActivity.showFragment(fragment, true, null)
        }
    }

    override fun displayCluster(fromActivity: AppCompatActivity, searchFilters: SearchFilters) {
        if (fromActivity is MainActivity) {
            // TODO Analytics
            analyticsManager.trackScreenEvent(SCREEN_SEARCH_CLUSTER)
            val fragment = ClusterFragment.newInstance(searchFilters)
            fragment.enterTransition = Fade(Fade.IN).setDuration(200)
            fragment.returnTransition = Fade(Fade.OUT).setDuration(200)
            fromActivity.showFragment(fragment, true, null)
        }
    }

    override fun displayLocationSelection(fromActivity: AppCompatActivity) {
        if (fromActivity is MainActivity) {
            val fragment = LocationFragment.newInstance()
            fragment.enterTransition = Fade(Fade.IN).setDuration(200)
            fragment.returnTransition = Fade(Fade.OUT).setDuration(200)
            fromActivity.showFragment(fragment, true, null)
        }
    }


    override fun pressBack(fromActivity: AppCompatActivity) {
        hideKeyboard(fromActivity)
        if (fromActivity is MainActivity) {
            fromActivity.onBackPressed()
        }
    }

    private fun hideKeyboard(fromActivity: AppCompatActivity) {
        val view = fromActivity.currentFocus
        if (view != null) {
            val imm = fromActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}