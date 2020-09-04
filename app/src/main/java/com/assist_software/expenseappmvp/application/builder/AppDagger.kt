package com.assist_software.expenseappmvp.application.builder

import android.content.Context
import com.assist_software.expenseappmvp.data.database.AppDatabase
import com.assist_software.expenseappmvp.data.database.repositories.ExpenseRepository
import com.assist_software.expenseappmvp.data.database.repositories.IncomeRepository
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxBus
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import com.google.firebase.auth.FirebaseAuth
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
annotation class AppScope

@Module
class AppModule(private val context: Context) {

    @Provides
    @AppScope
    fun context(): Context {
        return context
    }
}

@AppScope
@Component(
    modules = [(AppModule::class), (DatabaseServiceModule::class), (RxModule::class),
        (RestServiceModule::class), (GsonModule::class), (NetworkModule::class), (FirebaseAuthModule::class), (SharedPrefModule::class)]
)
interface AppComponent {

    fun context(): Context

    fun rxSchedulers(): RxSchedulers

    fun rxBus(): RxBus

    fun retrofit(): Retrofit

    fun appDatabase(): AppDatabase

    fun userRepository(): UserRepository

    fun getAuth(): FirebaseAuth

    fun initSharedPref(): SharedPrefUtils

    fun incomeRepository(): IncomeRepository

    fun expenseRepository(): ExpenseRepository
}