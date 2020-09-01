package com.assist_software.expenseappmvp.screens.splashScreen

import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SplashPresenter(
    private val view: SplashView,
    private val rxSchedulers: RxSchedulers,
    private val userRepository: UserRepository,
    private val sharedPref: SharedPrefUtils,
    private val compositeDisposables: CompositeDisposable
) {

    private val loggedUserId = 0L

    fun onCreate() {
        compositeDisposables.add(verifyUserLoggedIn())
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    private fun verifyUserLoggedIn(): Disposable {
        return Observable.timer(SPLASH_SCREEN_DURATION, TimeUnit.SECONDS)
            .observeOn(rxSchedulers.background())
            .observeOn(rxSchedulers.androidUI())
            .subscribe {
                if (sharedPref.hasKey(Constants.USER_ID)) {
                    view.showMainScreen()
                } else {
                    view.showLoginScreen()
                }
            }
    }

    companion object {
        const val SPLASH_SCREEN_DURATION = 2L
    }
}