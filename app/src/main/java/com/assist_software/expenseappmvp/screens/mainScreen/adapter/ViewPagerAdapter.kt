package com.assist_software.expenseappmvp.screens.mainScreen.adapter

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.BudgetFragment
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.ExpensesFragment

class ViewPagerAdapter(fa: FragmentActivity,private var numOfTabs: Int) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                BudgetFragment()
            }
            1 -> {
                ExpensesFragment()
            }
            else -> {
                BudgetFragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return numOfTabs
    }
}