package com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.FragmentDialogListener
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.TransactionAdapter
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.models.Transaction
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.dialog.ExpenseDialog
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_expenses.view.*

class ExpenseView(
    private val fragment: ExpensesFragment,
    inflater: LayoutInflater,
    container: ViewGroup?
) {
    val layout: View = inflater.inflate(R.layout.fragment_expenses, container, false)
    private lateinit var adapter: TransactionAdapter

    fun initSegmentComponents() {
        layout.interval_segment_view.setText(0, layout.resources.getString(R.string.this_week))
        layout.interval_segment_view.setText(1, layout.resources.getString(R.string.this_month))
        layout.interval_segment_view.setText(2, layout.resources.getString(R.string.this_year))
    }


    fun setTotalExpense(expense: Double) {
        layout.total_expense_value.text = expense.toString()
    }

    fun showDialog(transaction: Transaction, listener: FragmentDialogListener) {
        val fm: FragmentManager = fragment.childFragmentManager
        val dialogFragment = ExpenseDialog(transaction, listener)
        dialogFragment.show(fm, "fragmentDialog")
    }

    fun getAdapterItemList(): List<Transaction> {
        return adapter.getList()
    }

    fun initAdapter(orderedList: List<Transaction>, listener: PublishSubject<Transaction>) {
        layout.run {
            TransactionAdapter(layout.context!!, orderedList.toMutableList(), listener).run {
                transactions_recycler.adapter = this@run
                this@ExpenseView.adapter = this@run
            }
            transactions_recycler.layoutManager = LinearLayoutManager(layout.context)
        }
    }

    fun removeItemFromAdapter(index: Int) {
        adapter.removeItem(index)
    }
}