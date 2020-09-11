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

    fun deleteIncomeById(incomeId: Long){
        return db.incomeDao().deleteIncomeById(incomeId)
    }

    fun editIncome(id: Long, new_income: Double): Single<Int>{
        return db.incomeDao().editIncome(id, new_income)
    }

    fun editUserBalance(uid: String, new_income: Double, old_value: Double): Single<Int>{
        return db.incomeDao().editUserBalance(uid, new_income, old_value)
    }

    fun getIncomeImage(id: Long): Single<ByteArray>{
        return db.incomeDao().getIncomeImage(id)
    }
}