package com.assist_software.expenseappmvp.screens.mainScreen

import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class HomePresenter(
    private val view: HomeView,
    private val sharedPref: SharedPrefUtils,
    private val rxSchedulers: RxSchedulers,
    private val compositeDisposables: CompositeDisposable
) {

    private fun onAddActionClick(): Disposable {
        return view.goToAddActionScreen()
            .observeOn(rxSchedulers.androidUI())
            .subscribe {
                view.showAddActionScreen()
            }
    }

    private fun onLogOutClick(): Disposable {
        return view.logOutUser()
            .observeOn(rxSchedulers.androidUI())
            .throttleFirst(THROTTLE_DURATION, TimeUnit.SECONDS)
            .doOnNext { sharedPref.clear() }
            .subscribe {
                view.showLoginScreen()
            }
    }

    fun onCreate() {
        compositeDisposables.addAll(onAddActionClick(), onLogOutClick())
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    companion object {
        const val THROTTLE_DURATION = 1L
    }
}