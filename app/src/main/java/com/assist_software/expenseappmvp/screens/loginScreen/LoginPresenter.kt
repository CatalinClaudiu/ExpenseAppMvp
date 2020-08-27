package com.assist_software.expenseappmvp.screens.loginScreen

import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.screens.registerScreen.RegisterPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class LoginPresenter(
    private val view: LoginView,
    private val rxSchedulers: RxSchedulers,
    private val userRepository: UserRepository,
    private val compositeDisposables: CompositeDisposable
) {

    fun onCreate() {
        compositeDisposables.add(onRegisterClick())
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    private fun onRegisterClick(): Disposable {
        return view.goToRegisterScreen()
            .throttleFirst(RegisterPresenter.THROTTLE_DURATION, TimeUnit.SECONDS)
            .subscribe {
                view.showRegisterScreen()
            }
    }
}