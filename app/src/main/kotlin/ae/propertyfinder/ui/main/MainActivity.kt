package ae.propertyfinder.ui.main

import ae.propertyfinder.R
import ae.propertyfinder.account.ui.account.AccountFragment
import com.ysdc.comet.common.data.prefs.PrefsConstants.NOTIFICATION_TAB
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.ui.base.BaseActivity
import com.ysdc.comet.common.ui.base.BaseFragment
import com.ysdc.comet.common.ui.utils.FragmentAdapter
import com.ysdc.comet.common.utils.AppConstants
import ae.propertyfinder.model.Animation
import ae.propertyfinder.model.NavigationItem
import ae.propertyfinder.search.ui.search.SearchFragment
import ae.propertyfinder.ui.UiManager
import ae.propertyfinder.ui.save.SaveFragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.subkhansarif.bottomnavbar.IBottomClickListener
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMvpView, IBottomClickListener {

    private var curFragment: BaseFragment? = null
    private lateinit var adapter: FragmentAdapter
    private lateinit var bottomNavigation: BottomNavigationView

    @Inject
    internal lateinit var presenter: MainMvpPresenter<MainMvpView>
    @Inject
    internal lateinit var propertyFinderPreferences: PropertyFinderPreferences
    @Inject
    internal lateinit var uiManager: UiManager

    private lateinit var searchFragment: SearchFragment
    private lateinit var saveFragment: SaveFragment
    private lateinit var accountFragment: AccountFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onAttach(this)

        initView()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    private fun initView() {
        setSupportActionBar(customToolbar as Toolbar)
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_HOME or ActionBar.DISPLAY_SHOW_TITLE

        val menus = uiManager.getMenuItems(this)
        bottom_navbar.setMenu(menus)

        adapter = FragmentAdapter(supportFragmentManager)
        for (i in 0 until menus.size) {
            val menuEntry = menus[i]
            when (menuEntry.id) {
                NavigationItem.SEARCH.ordinal.toLong() -> {
                    searchFragment = SearchFragment.newInstance(i)
                    adapter.addFragments(searchFragment)
                }
                NavigationItem.SAVE.ordinal.toLong() -> {
                    saveFragment = SaveFragment.newInstance(i)
                    adapter.addFragments(saveFragment)
                }
                NavigationItem.ACCOUNT.ordinal.toLong() -> {
                    accountFragment = AccountFragment.newInstance(i)
                    adapter.addFragments(accountFragment)
                }
            }
        }

        homeContainer.adapter = adapter
        homeContainer.offscreenPageLimit = adapter.count
        homeContainer.setPagingEnabled(false)

        curTabId = propertyFinderPreferences.getAsInt(NOTIFICATION_TAB, generalConfig.defaultTabIndex())
        bottom_navbar.setSelected(curTabId)
        bottom_navbar.setMenuClickListener(this)
        propertyFinderPreferences.removeKey(NOTIFICATION_TAB)
        homeContainer.currentItem = curTabId
        updateCurrentMenu(adapter.getItemAtPosition(curTabId))
    }

    override fun onBackPressed() {

        removeCurrentFragment(curFragment)

        val fragment = popFragmentFromBackStack(curTabId)
        if (fragment != null) {
            backTo(curTabId, fragment as BaseFragment)
        } else {
            if (homeContainer.visibility == View.GONE) {
                backToRoot()
            } else {
                try {
                    super.onBackPressed()
                } catch (e : IllegalStateException) {
                    Timber.e(e)
                    Crashlytics.logException(Exception("Back Pressed State : ${e.printStackTrace()}"))
                }
            }
        }
    }


    override fun menuClicked(position: Int, id: Long) {
        hideKeyboard()
        if (position == curTabId) {
            if (needBackToRoot()) {
                Timber.d("current tab already selected: %d", curTabId)
                backToRoot()
                invalidateOptionsMenu()
            } else {
                updateCurrentMenu(adapter.getItemAtPosition(curTabId))
            }
        } else {
            if (curFragment != null) {
                curFragment!!.setHasOptionsMenu(false)
                pushFragmentToBackStack(curTabId, curFragment!!)
            }
            curTabId = position
            val fragment = popFragmentFromBackStack(curTabId) as BaseFragment?
            if (fragment != null) {
                replaceFragment(fragment, null)
            } else {
                backToRoot()
            }
            homeContainer.setCurrentItem(curTabId, true)
        }
    }

    private fun showTopAndBottomBars() {
        (barLayout as AppBarLayout).setExpanded(true, true)
    }

    private fun backTo(tabId: Int, fragment: BaseFragment) {
        if (curTabId != tabId) {
            curTabId = tabId
            bottomNavigation.selectedItemId = bottomNavigation.menu.getItem(curTabId).itemId
        }
        replaceFragment(fragment, null)
        try {
            supportFragmentManager.executePendingTransactions()
        } catch (e : IllegalStateException) {
            Timber.e(e)
            Crashlytics.logException(Exception("Fragment Transaction : ${e.printStackTrace()}"))
        }
    }

    private fun backToRoot() {

        fragmentContainer.visibility = View.GONE
        homeContainer.visibility = View.VISIBLE
        curFragment = null

        clearBackStack(curTabId)
        val fragment = adapter.getItemAtPosition(curTabId)
        if (fragment.isAdded) {
            requireBarElevation(fragment.shouldToolbarBeElevated())
            supportActionBar?.title = fragment.customTitle
            setActionBarVisible(fragment.isActionBarVisible)

            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        updateCurrentMenu(fragment)
    }

    private fun requireBarElevation(required: Boolean) {
        barLayout.elevation =
            if (required) resources.getDimension(R.dimen.default_elevation) else resources.getDimension(R.dimen.no_elevation)
    }

    private fun setActionBarVisible(actionBarVisible: Boolean) {
        if (actionBarVisible != supportActionBar?.isShowing) {
            if (actionBarVisible) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
        }
    }

    /**
     * @return true if the viewpager is not visible (meaning we are looking at a detail fragment)
     */
    private fun needBackToRoot(): Boolean {
        return homeContainer.visibility == View.GONE
    }

    override fun showFragment(fragment: BaseFragment, addToBackStack: Boolean, animation: Animation?) {
        showTopAndBottomBars()
        if (curFragment != null && addToBackStack) {
            pushFragmentToBackStack(curTabId, curFragment!!)
        }
        replaceFragment(fragment, animation)
    }

    private fun replaceFragment(fragment: BaseFragment, animation: Animation?) {
        updateCurrentMenu(fragment)
        if (curFragment == null) {
            fragmentContainer.visibility = View.VISIBLE
            homeContainer.visibility = View.GONE
        }
        requireBarElevation(fragment.shouldToolbarBeElevated())
        supportActionBar?.title = fragment.customTitle
        setActionBarVisible(fragment.isActionBarVisible)

        removeCurrentFragment(fragment)
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        animation?.let {
            when (it) {
                Animation.FROM_TOP ->
                    tr.setCustomAnimations(
                        R.anim.enter_from_top,
                        R.anim.exit_to_bottom,
                        R.anim.enter_from_bottom,
                        R.anim.exit_to_top
                    )
                else -> {
                    //Nothing to do yet
                }
            }
        }

        tr.replace(R.id.fragmentContainer, fragment, fragment.javaClass.name)
        tr.commitAllowingStateLoss()

        curFragment = fragment
    }

    private fun removeCurrentFragment(fragToRemove: Fragment?) {
        if (fragToRemove != null) {
            val fragment = supportFragmentManager.findFragmentByTag(fragToRemove.javaClass.name)
            if (fragment != null) {
                supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
    }

    companion object {
        fun newInstance(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            return intent
        }
    }
}