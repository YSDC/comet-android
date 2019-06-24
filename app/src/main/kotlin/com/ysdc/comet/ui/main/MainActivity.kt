package com.ysdc.comet.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.subkhansarif.bottomnavbar.BottomMenu
import com.subkhansarif.bottomnavbar.IBottomClickListener
import com.ysdc.comet.R
import com.ysdc.comet.common.ui.base.BaseActivity
import com.ysdc.comet.common.ui.utils.FragmentAdapter
import com.ysdc.comet.model.NavigationItem
import com.ysdc.comet.ui.event.EventFragment
import com.ysdc.comet.ui.profile.ProfileFragment
import com.ysdc.comet.ui.ranking.RankingFragment
import com.ysdc.comet.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

const val DEFAULT_MENU = 0

class MainActivity : BaseActivity(), MainMvpView, IBottomClickListener {

    private lateinit var adapter: FragmentAdapter
    private lateinit var bottomNavigation: BottomNavigationView
    @Inject
    lateinit var presenter: MainMvpPresenter<MainMvpView>



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
        bottom_navbar.setMenu(getMenu())

        adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragments(EventFragment.newInstance())
        adapter.addFragments(RankingFragment.newInstance())
        adapter.addFragments(ProfileFragment.newInstance())
        adapter.addFragments(SettingsFragment.newInstance())

        homeContainer.adapter = adapter
        homeContainer.offscreenPageLimit = adapter.count
        homeContainer.setPagingEnabled(false)

        bottom_navbar.setSelected(DEFAULT_MENU)
        bottom_navbar.setMenuClickListener(this)

        homeContainer.currentItem = DEFAULT_MENU
    }

    override fun menuClicked(position: Int, id: Long) {
        hideKeyboard()
        homeContainer.setCurrentItem(position, true)
    }

    private fun getMenu() : List<BottomMenu>{
        var menu: MutableList<BottomMenu> = ArrayList()
        menu.add(BottomMenu(NavigationItem.EVENT.ordinal.toLong(), getString(R.string.navbar_events), R.drawable.ic_events, null))
        menu.add(BottomMenu(NavigationItem.RANKING.ordinal.toLong(), getString(R.string.navbar_ranking), R.drawable.ic_ranking, null))
        menu.add(BottomMenu(NavigationItem.PROFILE.ordinal.toLong(), getString(R.string.navbar_profile), R.drawable.ic_profile, null))
        menu.add(BottomMenu(NavigationItem.SETTINGS.ordinal.toLong(), getString(R.string.navbar_settings), R.drawable.ic_settings, null))
        return menu
    }

    companion object {
        fun newInstance(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            return intent
        }
    }
}