package com.assist_software.expenseappmvp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assist_software.expenseappmvp.data.database.entities.Expense
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ExpenseDao {
    @Query("UPDATE users SET userCurrentBalance = userCurrentBalance - :new_expense WHERE uid = :id")
    fun updateUserExpense(id: String, new_expense: Double): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateExpense(expense: Expense): Single<Long>

    @Query("SELECT SUM(expenseAmount) FROM expenses WHERE expenseDate BETWEEN :startDate AND :endDate AND uid=:uid GROUP BY :uid")
    fun getUserExpenseByDate(startDate: Long, endDate: Long, uid: String): Maybe<Double>

    @Query("DELETE FROM expenses WHERE expenseId = :expenseId")
    fun deleteExpenseById(expenseId: Long)
}