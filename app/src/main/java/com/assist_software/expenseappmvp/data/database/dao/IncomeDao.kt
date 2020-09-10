package com.assist_software.expenseappmvp.data.database.dao

import androidx.room.*
import com.assist_software.expenseappmvp.data.database.entities.Income
import io.reactivex.Single

@Dao
interface IncomeDao {
    @Query("UPDATE users SET userCurrentBalance = userCurrentBalance + :new_income WHERE uid = :id")
    fun updateUserIncome(id: String, new_income: Double): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateIncome(income: Income): Single<Long>

    @Query("DELETE FROM incomes WHERE incomeId = :incomeId")
    fun deleteIncomeById(incomeId: Long)
}