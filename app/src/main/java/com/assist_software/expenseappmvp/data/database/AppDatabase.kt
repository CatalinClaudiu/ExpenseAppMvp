package com.assist_software.expenseappmvp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assist_software.expenseappmvp.data.database.dao.UserDao
import com.assist_software.expenseappmvp.data.database.entities.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}