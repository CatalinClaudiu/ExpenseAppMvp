package com.assist_software.expenseappmvp.screens.mainScreen.fragments.budget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.database.models.UserWithExpenses
import com.assist_software.expenseappmvp.utils.DateUtils
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_budget.view.*

class BudgetView(
    private val fragment: BudgetFragment,
    inflater: LayoutInflater,
    container: ViewGroup?
) {
    val layout: View = inflater.inflate(R.layout.fragment_budget, container, false)
    private var barChart = layout.details_barChart

    fun setUserCurrentBalance(balance: Double) {
        layout.balance_value_textView.text = balance.toString()
    }

    fun setTodayExpenses(expense: Double) {
        layout.today_expense_value_textView.text = expense.toString()
    }

    fun setWeekExpense(expense: Double) {
        layout.week_expense_value_textView.text = expense.toString()
    }

    fun setMonthExpense(expense: Double) {
        layout.month_expense_value_textView.text = expense.toString()
    }

    private fun computeMonthCashFlow(
        userWithExpenses: UserWithExpenses,
        startDate: Long,
        endDate: Long
    ): Double {
        val expensesSum =
            userWithExpenses.expenses.filter { ex -> ex.expenseDate in startDate..endDate }
                .sumByDouble { it.expenseAmount.toDouble() }
        val incomeSum =
            userWithExpenses.incomes.filter { ex -> ex.incomeDate in startDate..endDate }
                .sumByDouble { it.incomeAmount.toDouble() }

        return incomeSum - expensesSum
    }

    fun setUpChart(userDetails: UserWithExpenses) {
        val list: ArrayList<BarEntry> = ArrayList()
        val colors: ArrayList<Int> = ArrayList()
        val chartMap: LinkedHashMap<String, Double> = linkedMapOf()

        val balance = mutableListOf<Double>()
        val xAxisLabel = mutableListOf<String>()

        var endDate = System.currentTimeMillis()
        for (i in 0..11) {
            val startDate = DateUtils.getOneMonthAgoTime(endDate)
            chartMap[DateUtils.getMonthName(startDate)] =
                computeMonthCashFlow(userDetails, startDate, endDate)
            endDate = startDate - 1000
        }

        chartMap.forEach { (s, d) ->
            xAxisLabel.add(s)
            balance.add(d)
        }
        xAxisLabel.reverse()
        balance.reverse()

        for (i in balance.indices) {
            list.add(BarEntry(i.toFloat(), balance[i].toFloat()))
            if (balance[i] >= 0) {
                colors.add(layout.resources.getColor(R.color.colorChartGreen))
            } else {
                colors.add(layout.resources.getColor(R.color.colorRed))
            }
        }

        setAppearanceForChart(xAxisLabel, list, colors)
    }

    private fun setAppearanceForChart(
        xAxisLabel: MutableList<String>,
        list: List<BarEntry>,
        colors: ArrayList<Int>
    ) {
        val barData = BarData() //all the data for chart
        val dataSet = BarDataSet(list, "")

        dataSet.colors = colors
        dataSet.setValueTextColors(colors)
        dataSet.valueTextSize = 10f

        barData.addDataSet(dataSet)
        barData.barWidth = 0.7f

        //set up the x coordinate
        val xAxis = barChart.xAxis
        val yAxis = barChart.axisLeft
        xAxis.labelCount = xAxisLabel.size + 1
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(true)
        yAxis.setDrawGridLines(false)

        //set up legend
        val colors: Array<Int> = arrayOf<Int>(
            layout.resources.getColor(R.color.colorChartGreen),
            layout.resources.getColor(R.color.colorRed)
        )
        barChart.legend.isEnabled = true
        barChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        val l1 = LegendEntry(
            layout.resources.getString(R.string.positive_balance),
            Legend.LegendForm.SQUARE,
            10f,
            2f,
            null,
            colors[0]
        )
        val l2 = LegendEntry(
            layout.resources.getString(R.string.negative_balance),
            Legend.LegendForm.SQUARE,
            10f,
            2f,
            null,
            colors[1]
        )
        barChart.legend.setCustom(arrayOf(l1, l2))

        //modify the default appearance
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.description.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.xAxis.textSize = 10f
        barChart.setExtraOffsets(0f, 0f, 0f, 1f)
        barChart.axisLeft.textSize = 14f
        barChart.data = barData
        barChart.invalidate()
    }
}