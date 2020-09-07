package com.assist_software.expenseappmvp.data.database.repositories

import com.assist_software.expenseappmvp.data.database.AppDatabase
import com.assist_software.expenseappmvp.data.database.entities.Income
import io.reactivex.Single

class IncomeRepository(private val db: AppDatabase) {
    fun updateUserIncome(uid: String, new_income: Double): Single<Int> {
        return db.incomeDao().updateUserIncome(uid, new_income)
    }

    fun insertIncome(income: Income): Single<Long> {
        return db.incomeDao().insertOrUpdateIncome(income)
    }
}