package com.assist_software.expenseappmvp.screens.mainScreen

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.addActionScreen.AddActionActivity
import com.assist_software.expenseappmvp.screens.currencyConverterScreen.CurrencyConverterActivity
import com.assist_software.expenseappmvp.screens.loginScreen.LoginActivity
import com.assist_software.expenseappmvp.screens.mainScreen.adapter.ViewPagerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.home_nav_header.view.*
import kotlinx.android.synthetic.main.home_toolbar.view.*

class HomeView(var activity: HomeActivity) : NavigationView.OnNavigationItemSelectedListener {
    val layout: View = View.inflate(activity, R.layout.activity_main, null)
    private val navigationDrawer = layout.home_drawer_layout
    lateinit var toolbar: Toolbar
    private val fragmentAdapter = ViewPagerAdapter(activity, 2)

    init {
        layout.viewPager_fragment_container.adapter = fragmentAdapter
        TabLayoutMediator(
            layout.bottom_tab_layout,
            layout.viewPager_fragment_container
        ) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.icon = ContextCompat.getDrawable(activity, R.drawable.ic_budget)
                    tab.text = activity.resources.getString(R.string.budget)
                    layout.viewPager_fragment_container.currentItem = pos
                }
                1 -> {
                    tab.icon = ContextCompat.getDrawable(activity, R.drawable.ic_expenses)
                    tab.text = activity.resources.getString(R.string.expense)
                    layout.viewPager_fragment_container.currentItem = pos
                }
                else -> {
                    throw Exception("Out of bounds tab layout")
                }
            }
        }.attach()
        layout.viewPager_fragment_container.currentItem = 0
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> navigationDrawer.closeDrawers()
            R.id.nav_converter -> CurrencyConverterActivity.start(activity)
        }
        navigationDrawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun showAddActionScreen() {
        AddActionActivity.start(activity)
    }

    fun goToAddActionScreen(): Observable<Any> {
        return RxView.clicks(layout.button_add_action)
    }

    fun initComponents() {
        layout.home_nav_view.setNavigationItemSelectedListener(this)
    }

    fun logOutUser(): Observable<Any> {
        return RxView.clicks(layout.logout_btn)
    }

    fun showLoginScreen() {
        LoginActivity.start(activity)
        activity.finish()
    }

    fun initToolbar() {
        toolbar = layout.home_tool_bar
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.title = activity.getString(R.string.my_budget)
        toolbar.setTitleTextAppearance(activity, R.style.HomeToolbarFont)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun initNavigationDrawer(toolbar: Toolbar) {
        val actionBarToggle = ActionBarDrawerToggle(
            activity,
            navigationDrawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        navigationDrawer.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
    }

    fun setUserName(userName: String) {
        layout.home_nav_view.getHeaderView(0).side_menu_user_name.text =
            activity.getString(R.string.hello_user, userName)
    }
}