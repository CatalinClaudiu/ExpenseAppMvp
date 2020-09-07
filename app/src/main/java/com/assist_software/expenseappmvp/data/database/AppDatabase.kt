package com.assist_software.expenseappmvp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assist_software.expenseappmvp.data.database.dao.ExpenseDao
import com.assist_software.expenseappmvp.data.database.dao.IncomeDao
import com.assist_software.expenseappmvp.data.database.dao.UserDao
import com.assist_software.expenseappmvp.data.database.entities.Expense
import com.assist_software.expenseappmvp.data.database.entities.Income
import com.assist_software.expenseappmvp.data.database.entities.User

@Database(entities = [User::class, Income::class, Expense::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao(): ExpenseDao
}