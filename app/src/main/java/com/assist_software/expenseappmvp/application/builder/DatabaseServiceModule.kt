package com.assist_software.expenseappmvp.application.builder

import android.content.Context
import com.assist_software.expenseappmvp.data.database.AppDatabase
import com.assist_software.expenseappmvp.data.database.RoomDB
import com.assist_software.expenseappmvp.data.database.repositories.ExpenseRepository
import com.assist_software.expenseappmvp.data.database.repositories.IncomeRepository
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import dagger.Module
import dagger.Provides

@Module
class DatabaseServiceModule {

    @AppScope
    @Provides
    fun appDatabase(context: Context): AppDatabase {
        return RoomDB(context).appDatabase
    }

    @AppScope
    @Provides
    fun userRepository(appDatabase: AppDatabase): UserRepository {
        return UserRepository(appDatabase)
    }

    @AppScope
    @Provides
    fun incomeRepository(appDatabase: AppDatabase): IncomeRepository {
        return IncomeRepository(appDatabase)
    }

    @AppScope
    @Provides
    fun expenseRepository(appDatabase: AppDatabase): ExpenseRepository {
        return ExpenseRepository(appDatabase)
    }
}
