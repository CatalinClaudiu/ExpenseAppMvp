package com.assist_software.expenseappmvp.screens.splashScreen

import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import com.assist_software.expenseappmvp.utils.disposeBy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashPresenter(
    private val view: SplashView,
    private val rxSchedulers: RxSchedulers,
    private val sharedPref: SharedPrefUtils,
    private val compositeDisposables: CompositeDisposable
) {

    private fun verifyUserLoggedIn() {
        Observable.timer(SPLASH_SCREEN_DURATION, TimeUnit.SECONDS)
            .observeOn(rxSchedulers.androidUI())
            .subscribe {
                if (sharedPref.hasKey(Constants.USER_ID)) {
                    view.showMainScreen()
                } else {
                    view.showLoginScreen()
                }
            }.disposeBy(compositeDisposables)
    }

    fun onCreate() {
        verifyUserLoggedIn()
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    companion object {
        const val SPLASH_SCREEN_DURATION = 2L
    }
}