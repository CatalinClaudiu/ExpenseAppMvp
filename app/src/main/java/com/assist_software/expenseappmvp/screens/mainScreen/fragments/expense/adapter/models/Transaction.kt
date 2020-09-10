package com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.models

data class Transaction(val transactionId: Long, val date: Long, val amount: Double, val category: String, val name: String, var balance: Double, val details: String, val imageDetails: ByteArray)