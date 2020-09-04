package com.assist_software.expenseappmvp.data.database.repositories

import com.assist_software.expenseappmvp.data.database.AppDatabase

class ExpenseRepository(private val db: AppDatabase) {
    fun updateUserExpense(uid: String, new_expense: Double) {
        return db.expenseDao().updateUserExpense(uid, new_expense)
    }
}