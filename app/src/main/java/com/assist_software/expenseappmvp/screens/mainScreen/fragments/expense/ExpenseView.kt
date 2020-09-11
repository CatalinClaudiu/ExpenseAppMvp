package com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.database.entities.Expense
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.FragmentDialogListener
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.TransactionAdapter
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.models.Transaction
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.dialog.ExpenseDialog
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
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

    fun setUpChart(chartData: MutableList<Expense>) {
        val yValue: ArrayList<PieEntry> = ArrayList<PieEntry>()

        for (element in chartData) {
            yValue.add(PieEntry(element.expenseAmount.toFloat(), element.expenseCategory))
        }

        setAppearanceForChart(yValue)
    }

    private fun setAppearanceForChart(yValue: ArrayList<PieEntry>) {
        //data set formatting
        val dataSet = PieDataSet(yValue, "")
        dataSet.sliceSpace = 2F
        dataSet.selectionShift = 5f
        dataSet.setColors(
            Color.rgb(217, 80, 138),
            Color.rgb(254, 149, 7), Color.rgb(254, 247, 120),
            Color.rgb(106, 167, 134), Color.rgb(53, 194, 209),
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124)
        )

        var pieData: PieData = PieData(dataSet)
        pieData.setValueTextSize(10f)
        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueFormatter(PercentFormatter(layout.pieChart))

        //chart
        layout.pieChart.isRotationEnabled = false
        layout.pieChart.isDrawHoleEnabled = true
        layout.pieChart.setHoleColor(Color.WHITE)
        layout.pieChart.transparentCircleRadius = 61f
        layout.pieChart.setEntryLabelColor(Color.WHITE)
        layout.pieChart.data = pieData
        layout.pieChart.invalidate()
        layout.pieChart.setUsePercentValues(true)

        //legend setup
        val legend: Legend = layout.pieChart.legend
        legend.isEnabled = false

        //description setup
        val description: Description = layout.pieChart.description
        description.isEnabled = false
    }
}