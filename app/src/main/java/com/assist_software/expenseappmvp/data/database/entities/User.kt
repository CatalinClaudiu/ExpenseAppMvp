package com.assist_software.expenseappmvp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
        @PrimaryKey(autoGenerate = false) var uid: String = "",
        @ColumnInfo(name = "userName") var userName: String = "",
        @ColumnInfo(name = "userEmail") var userEmail: String = "",
        @ColumnInfo(name = "userPassword") var userPassword: String = "",
        @ColumnInfo(name = "userCurrentBalance") var userCurrentBalance: Double = 0.0
)