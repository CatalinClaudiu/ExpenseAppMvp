package com.assist_software.expenseappmvp.data.database.repositories

import com.assist_software.expenseappmvp.data.database.AppDatabase
import com.assist_software.expenseappmvp.data.database.entities.Expense

class ExpenseRepository(private val db: AppDatabase) {
    fun updateUserExpense(uid: String, new_expense: Double) {
        return db.expenseDao().updateUserExpense(uid, new_expense)
    }

    fun insertExpense(expense: Expense){
        return db.expenseDao().insertOrUpdateExpense(expense)
    }
}