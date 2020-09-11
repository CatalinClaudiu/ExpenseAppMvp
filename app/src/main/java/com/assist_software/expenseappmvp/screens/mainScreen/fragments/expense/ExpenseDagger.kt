package com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense

import android.view.LayoutInflater
import android.view.ViewGroup
import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.data.database.repositories.ExpenseRepository
import com.assist_software.expenseappmvp.data.database.repositories.IncomeRepository
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ExpenseScope

@ExpenseScope
@Component(
    modules = [ExpenseModule::class],
    dependencies = [AppComponent::class]
)
interface ExpenseComponent {
    fun inject(fragment: ExpensesFragment)
}

@Module
class ExpenseModule(
    private val fragment: ExpensesFragment,
    private val inflater: LayoutInflater,
    private val container: ViewGroup?
) {
    @Provides
    @ExpenseScope
    fun view(): ExpenseView {
        return ExpenseView(fragment, inflater, container)
    }

    @Provides
    @ExpenseScope
    fun presenter(
        view: ExpenseView,
        sharedPref: SharedPrefUtils,
        userRepository: UserRepository,
        incomeRepository: IncomeRepository,
        expenseRepository: ExpenseRepository,
        rxSchedulers: RxSchedulers
    ): ExpensePresenter {
        val compositeDisposable = CompositeDisposable()
        return ExpensePresenter(
            view,
            sharedPref,
            rxSchedulers,
            userRepository,
            incomeRepository,
            expenseRepository,
            compositeDisposable
        )
    }
}