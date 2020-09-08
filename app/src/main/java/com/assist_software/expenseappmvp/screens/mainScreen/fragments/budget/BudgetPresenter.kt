package com.assist_software.expenseappmvp.screens.mainScreen.fragments.budget

import com.assist_software.expenseappmvp.data.database.repositories.ExpenseRepository
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

class BudgetPresenter(
    private val view: BudgetView,
    private val sharedPref: SharedPrefUtils,
    private val rxSchedulers: RxSchedulers,
    private val userRepository: UserRepository,
    private val expenseRepository: ExpenseRepository,
    private val compositeDisposables: CompositeDisposable
) {

    private var uid: String = ""
    private var startDay: Long = 0L
    private var endDay: Long = 0L
    private var c: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    private var startMonth: Long = 0L
    private var startWeek: Long = 0L
    private var c1: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))


    fun onCreate() {
        initComponents()
        compositeDisposables.addAll(
            getCurrentBalance(),
            getTodayExpense(), getWeekExpense(), getMonthExpense()
        )
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    private fun initComponents() {
        if (sharedPref.hasKey(Constants.USER_ID)) {
            uid = sharedPref.read(Constants.USER_ID, "").toString()
        }

        startDay = Calendar.getInstance().timeInMillis - Constants.oneDayMs
        endDay = Calendar.getInstance().timeInMillis

        c.add(Calendar.MONTH, -1)
        startMonth = c.timeInMillis

        c1.add(Calendar.WEEK_OF_MONTH, -1)
        startWeek = c1.timeInMillis
    }

    private fun getCurrentBalance(): Disposable {
        return userRepository.getUserBalance(uid)
            .observeOn(rxSchedulers.androidUI())
            .doOnSuccess { balance ->
                view.setUserCurrentBalance(balance)
            }
            .subscribe()
    }

    private fun getTodayExpense(): Disposable {
        return expenseRepository.getExpenseByDate(startDay, endDay, uid)
            .observeOn(rxSchedulers.androidUI())
            .doOnSuccess { expense ->
                view.setTodayExpenses(expense)
            }
            .subscribe()
    }

    private fun getWeekExpense(): Disposable {
        return expenseRepository.getExpenseByDate(startWeek, endDay, uid)
            .observeOn(rxSchedulers.androidUI())
            .doOnSuccess {expense ->
                view.setWeekExpense(expense)
            }
            .subscribe()
    }

    private fun getMonthExpense(): Disposable {
        return expenseRepository.getExpenseByDate(startMonth, endDay, uid)
            .observeOn(rxSchedulers.androidUI())
            .doOnSuccess {expense ->
                view.setMonthExpense(expense)
            }
            .subscribe()
    }
}