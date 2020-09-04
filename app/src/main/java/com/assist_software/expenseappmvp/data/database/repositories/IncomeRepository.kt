package com.assist_software.expenseappmvp.data.database.repositories

import com.assist_software.expenseappmvp.data.database.AppDatabase
import com.assist_software.expenseappmvp.data.database.entities.Income

class IncomeRepository(private val db: AppDatabase) {
    fun updateUserIncome(uid: String, new_income: Double) {
        return db.incomeDao().updateUserIncome(uid, new_income)
    }

    fun insertIncome(income: Income){
        return db.incomeDao().insertOrUpdateIncome(income)
    }
}