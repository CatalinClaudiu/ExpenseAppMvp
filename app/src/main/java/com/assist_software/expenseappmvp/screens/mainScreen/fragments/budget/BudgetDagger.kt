package com.assist_software.expenseappmvp.screens.mainScreen.fragments.budget

import android.view.LayoutInflater
import android.view.ViewGroup
import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.data.database.repositories.ExpenseRepository
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
annotation class BudgetScope

@BudgetScope
@Component(
    modules = [BudgetModule::class],
    dependencies = [AppComponent::class]
)
interface BudgetComponent {
    fun inject(fragment: BudgetFragment)
}

@Module
class BudgetModule(
    private val fragment: BudgetFragment,
    private val inflater: LayoutInflater,
    private val container: ViewGroup?
) {

    @Provides
    @BudgetScope
    fun view(): BudgetView {
        return BudgetView(fragment, inflater, container)
    }

    @Provides
    @BudgetScope
    fun presenter(
        view: BudgetView,
        sharedPref: SharedPrefUtils,
        userRepository: UserRepository,
        expenseRepository: ExpenseRepository,
        rxSchedulers: RxSchedulers
    ): BudgetPresenter {
        val compositeDisposable = CompositeDisposable()
        return BudgetPresenter(
            view,
            sharedPref,
            rxSchedulers,
            userRepository,
            expenseRepository,
            compositeDisposable
        )
    }
}