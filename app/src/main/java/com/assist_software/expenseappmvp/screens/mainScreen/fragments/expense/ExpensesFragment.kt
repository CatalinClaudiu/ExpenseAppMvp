package com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assist_software.expenseappmvp.application.ExpenseApp
import com.assist_software.expenseappmvp.screens.mainScreen.HomeActivity
import javax.inject.Inject

class ExpensesFragment : Fragment() {

    @Inject
    lateinit var presenter: ExpensePresenter

    @Inject
    lateinit var view: ExpenseView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerExpenseComponent.builder().appComponent(context?.let { ExpenseApp.appComponent(it) })
            .expenseModule(ExpenseModule(this, inflater, container)).build().inject(this)
        presenter.onCreate()
        return view.layout
    }

    override fun onResume() {
        super.onResume()
        (this.activity as HomeActivity).view.toolbar.title = "My Expense"
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}