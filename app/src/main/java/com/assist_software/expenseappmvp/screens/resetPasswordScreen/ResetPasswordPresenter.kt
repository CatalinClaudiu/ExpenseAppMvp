package com.assist_software.expenseappmvp.screens.resetPasswordScreen

import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import com.assist_software.expenseappmvp.utils.Validations
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ResetPasswordPresenter(
    private val view: ResetPasswordView,
    private val rxSchedulers: RxSchedulers,
    private val auth: FirebaseAuth,
    private val compositeDisposables: CompositeDisposable
) {

    private val user: User = User()
    private var fullUser: User = User()

    private fun getUserEmail(): Disposable {
        return view.inputUserEmail()
            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
            .observeOn(rxSchedulers.androidUI())
            .filter { email -> Validations.emailValidation(email.toString()) }
            .subscribe { email -> user.userEmail = email.toString() }
    }

    private fun onResetPasswordClick(): Disposable {
        return view.resetPasswordClick()
            .observeOn(rxSchedulers.androidUI())
            .doOnNext { view.fieldValidation(user) }
            .filter { user.userEmail != "" }
            .doOnNext { view.showLoginScreen() }
            .observeOn(rxSchedulers.background())
            .subscribe({
                resetPasswordFromFirebase(user.userEmail)
            }, {
                Timber.i(it.localizedMessage)
            })
    }

    private fun resetPasswordFromFirebase(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { p0 ->
            Observable.just(p0)
                .observeOn(rxSchedulers.androidUI())
                .subscribe({
                    view.showMessage(p0.isSuccessful)
                }, { Timber.e(it.localizedMessage) })
        }
    }

    fun onCreate() {
        compositeDisposables.addAll(getUserEmail(), onResetPasswordClick())
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }
}