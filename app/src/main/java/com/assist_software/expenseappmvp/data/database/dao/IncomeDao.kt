package com.assist_software.expenseappmvp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single

@Dao
interface IncomeDao {
    @Query("UPDATE users SET userCurrentBalance = userCurrentBalance + :new_income WHERE uid = :id")
    fun updateUserIncome(id: String, new_income: Double)
}