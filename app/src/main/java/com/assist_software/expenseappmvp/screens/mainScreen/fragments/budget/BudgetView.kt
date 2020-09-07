package com.assist_software.expenseappmvp.screens.mainScreen.fragments.budget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assist_software.expenseappmvp.R
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_budget.view.*

class BudgetView(fragment: BudgetFragment, inflater: LayoutInflater, container: ViewGroup?) {
    val layout: View = inflater.inflate(R.layout.fragment_budget, container, false)

    fun setUserCurrentBalance(balance: Double){
        layout.balance_value_textView.text = balance.toString()
    }

    fun setTodayExpenses(expense: Double){
        layout.today_expense_value_textView.text = expense.toString()
    }

    fun setWeekExpense(expense: Double){
        layout.week_expense_value_textView.text = expense.toString()
    }

    fun setMonthExpense(expense: Double){
        layout.month_expense_value_textView.text = expense.toString()
    }
}