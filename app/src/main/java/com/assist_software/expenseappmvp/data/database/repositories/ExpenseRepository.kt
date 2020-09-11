package com.assist_software.expenseappmvp.data.database.repositories

import com.assist_software.expenseappmvp.data.database.AppDatabase
import com.assist_software.expenseappmvp.data.database.entities.Expense
import io.reactivex.Maybe
import io.reactivex.Single

class ExpenseRepository(private val db: AppDatabase) {
    fun updateUserExpense(uid: String, new_expense: Double): Single<Int> {
        return db.expenseDao().updateUserExpense(uid, new_expense)
    }

    fun insertExpense(expense: Expense): Single<Long> {
        return db.expenseDao().insertOrUpdateExpense(expense)
    }

    fun getExpenseByDate(startDate: Long, endDate: Long, uid: String): Maybe<Double> {
        return db.expenseDao().getUserExpenseByDate(startDate, endDate, uid)
    }

    fun deleteExpenseById(expenseId: Long) {
        return db.expenseDao().deleteExpenseById(expenseId)
    }

    fun editExpense(id: Long, new_expense: Double): Single<Int>{
        return db.expenseDao().editExpense(id, new_expense)
    }

    fun editUserBalance(uid: String, new_expense: Double, old_value: Double): Single<Int>{
        return db.expenseDao().editUserExpense(uid, new_expense, old_value)
    }

    fun getExpenseImage(id: Long): Single<ByteArray>{
        return db.expenseDao().getExpenseImage(id)
    }
}