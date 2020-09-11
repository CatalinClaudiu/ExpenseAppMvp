package com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter

import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.models.Transaction

interface FragmentDialogListener {
    fun onDeleteClick(transactionItem: Transaction)
}