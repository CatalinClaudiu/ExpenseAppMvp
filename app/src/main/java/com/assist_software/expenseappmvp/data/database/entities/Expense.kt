package com.assist_software.expenseappmvp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) var expenseId: Long = 0,
    @ForeignKey(entity = User::class, parentColumns = ["uid"], childColumns = ["uid"]) var uid: String,
    @ColumnInfo(name = "expenseDate") var expenseDate: Long,
    @ColumnInfo(name = "expenseAmount") var expenseAmount: Double,
    @ColumnInfo(name = "expenseCategory") var expenseCategory: String,
    @ColumnInfo(name = "expenseDetails") var expenseDetails: String,
    @ColumnInfo(name = "expenseImage") var expenseImage: ByteArray
)