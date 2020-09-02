package com.assist_software.expenseappmvp.screens.mainScreen

import android.view.View
import androidx.core.content.ContextCompat
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.addActionScreen.AddActionActivity
import com.assist_software.expenseappmvp.screens.mainScreen.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.home_toolbar.view.*

class HomeView(var activity: HomeActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_main, null)

    init {
        val fragmentAdapter = ViewPagerAdapter(activity, 2)
        layout.viewPager_fragment_container.adapter = fragmentAdapter

        TabLayoutMediator(layout.bottom_tab_layout, layout.viewPager_fragment_container) { tab, pos ->
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

    fun showAddActionScreen() {
        AddActionActivity.start(activity)
        activity.finish()
    }

    fun goToAddActionScreen(): Observable<Any> {
        return RxView.clicks(layout.button_add_action)
    }
}