package com.assist_software.expenseappmvp.screens.registerScreen

import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


class RegisterPresenter(
    private val view: RegisterView,
    private val rxSchedulers: RxSchedulers,
    private val userRepository: UserRepository,
    private val compositeDisposables: CompositeDisposable
) {

    private val user: User = User()

    fun onCreate() {
        compositeDisposables.addAll(onLoginClick(), registerUser())
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    private fun onLoginClick(): Disposable {
        return view.goToLoginScreen()
            .throttleFirst(THROTTLE_DURATION, TimeUnit.SECONDS)
            .subscribe {
                view.showLoginScreen()
            }
    }

    private fun registerUser(): Disposable {
        return view.registerUser()
            .observeOn(rxSchedulers.background())
            .flatMap {
                userRepository.savePrimaryUser(user).toObservable()
            }
            .observeOn(rxSchedulers.androidUI())
            .subscribe {
                view.showLoginScreen()
            }
    }

    companion object {
        const val THROTTLE_DURATION = 1L
    }
}