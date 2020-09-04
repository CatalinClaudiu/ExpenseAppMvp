package com.assist_software.expenseappmvp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Incomes")
public class Income(
    @PrimaryKey(autoGenerate = true) var incomeId: Long = 0,
    @ForeignKey(entity = User::class, parentColumns = ["uid"], childColumns = ["uid"]) var uid: String,
    @ColumnInfo(name = "incomeDate") var incomeDate: Long,
    @ColumnInfo(name = "incomeAmount") var incomeAmount: Double,
    @ColumnInfo(name = "incomeCategory") var incomeCategory: String,
    @ColumnInfo(name = "incomeDetails") var incomeDetails: String,
    @ColumnInfo(name = "incomeImage") var incomeImage: ByteArray
)