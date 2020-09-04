package com.assist_software.expenseappmvp.data.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Query("UPDATE users SET userCurrentBalance = userCurrentBalance - :new_expense WHERE uid = :id")
    fun updateUserExpense(id: String, new_expense: Double)
}