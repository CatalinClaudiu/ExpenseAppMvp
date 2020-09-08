package com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assist_software.expenseappmvp.R
import kotlinx.android.synthetic.main.fragment_expenses.view.*

class ExpenseView(
    private val fragment: ExpensesFragment,
    inflater: LayoutInflater,
    container: ViewGroup?
) {
    val layout: View = inflater.inflate(R.layout.fragment_expenses, container, false)

    fun initSegmentComponents() {
        layout.interval_segment_view.setText(0, layout.resources.getString(R.string.this_week))
        layout.interval_segment_view.setText(1, layout.resources.getString(R.string.this_month))
        layout.interval_segment_view.setText(2, layout.resources.getString(R.string.this_year))
    }


    fun setTotalExpense(expense: Double) {
        layout.total_expense_value.text = expense.toString()
    }
}