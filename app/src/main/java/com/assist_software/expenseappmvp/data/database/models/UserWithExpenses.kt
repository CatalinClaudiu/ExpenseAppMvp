package com.assist_software.expenseappmvp.data.database.models

import androidx.room.Embedded
import androidx.room.Relation
import com.assist_software.expenseappmvp.data.database.entities.Expense
import com.assist_software.expenseappmvp.data.database.entities.Income
import com.assist_software.expenseappmvp.data.database.entities.User

data class UserWithExpenses(
    @Embedded
    var userDetails: User,
    @Relation(parentColumn = "uid", entityColumn = "uid", entity = Income::class)
    var incomes: MutableList<Income>,
    @Relation(parentColumn = "uid", entityColumn = "uid", entity = Expense::class)
    var expenses: MutableList<Expense>
)