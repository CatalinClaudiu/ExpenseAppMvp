package com.assist_software.expenseappmvp.screens.mainScreen.fragments.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assist_software.expenseappmvp.application.ExpenseApp
import javax.inject.Inject

class BudgetFragment : Fragment() {

    @Inject
    lateinit var presenter: BudgetPresenter

    @Inject
    lateinit var view: BudgetView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerBudgetComponent.builder().appComponent(context?.let { ExpenseApp.appComponent(it) })
            .budgetModule(BudgetModule(this, inflater, container)).build().inject(this)
        presenter.onCreate()
        return view.layout
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}