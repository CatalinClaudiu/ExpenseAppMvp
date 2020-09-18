package com.assist_software.expenseappmvp.screens.addActionScreen

import android.content.Context
import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.application.builder.RestServiceNotification
import com.assist_software.expenseappmvp.data.database.repositories.ExpenseRepository
import com.assist_software.expenseappmvp.data.database.repositories.IncomeRepository
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AddActionScope

@AddActionScope
@Component(
    modules = [AddActionModule::class],
    dependencies = [AppComponent::class]
)
interface AddActionComponent {
    fun inject(activity: AddActionActivity)
}

@Module
class AddActionModule(private val activity: AddActionActivity) {

    @Provides
    @AddActionScope
    fun view(): AddActionView {
        return AddActionView(activity)
    }

    @Provides
    @AddActionScope
    fun provideRxPermissions(context: Context): RxPermissions {
        return RxPermissions(activity)
    }

    @Provides
    @AddActionScope
    fun presenter(
        view: AddActionView,
        rxSchedulers: RxSchedulers,
        incomeRepository: IncomeRepository,
        expenseRepository: ExpenseRepository,
        restNotification: RestServiceNotification,
        sharedPref: SharedPrefUtils,
        rxPermissions: RxPermissions
    ): AddActionPresenter {
        val compositeDisposable = CompositeDisposable()
        return AddActionPresenter(
            view,
            rxSchedulers,
            rxPermissions,
            incomeRepository,
            expenseRepository,
            restNotification,
            sharedPref,
            compositeDisposable
        )
    }
}