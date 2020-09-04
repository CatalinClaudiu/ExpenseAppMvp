package com.assist_software.expenseappmvp.data.database.repositories

import com.assist_software.expenseappmvp.data.database.AppDatabase

class IncomeRepository(private val db: AppDatabase) {
    fun saveUserIncome(uid: String, new_income: Double) {
        return db.incomeDao().updateUserIncome(uid, new_income)
    }
}