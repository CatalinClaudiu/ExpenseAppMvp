package com.assist_software.expenseappmvp.screens.registerScreen

import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.Validations
import com.assist_software.expenseappmvp.utils.disposeBy
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RegisterPresenter(
    private val view: RegisterView,
    private val rxSchedulers: RxSchedulers,
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val compositeDisposables: CompositeDisposable
) {
    private val user: User = User()

    private fun onLoginClick(): Disposable {
        return view.goToLoginScreen()
            .throttleFirst(THROTTLE_DURATION, TimeUnit.SECONDS)
            .subscribe {
                view.showLoginScreen()
            }
    }

    private fun registerUser(): Disposable {
        return view.registerUserClick()
            .observeOn(rxSchedulers.androidUI())
            .doOnNext { view.fieldValidation(user) }
            .filter {
                !(user.userName == "" || user.userEmail == "" || user.userPassword == "")
            }
            .observeOn(rxSchedulers.background())
            .subscribe({ registerUserToFirebase(user.userEmail, user.userPassword) }, {
                Timber.i(it.localizedMessage)
            })
    }

    private fun getUserName(): Disposable {
        return view.inputUserName()
            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
            .observeOn(rxSchedulers.androidUI())
            .filter { name -> Validations.nameValidation(name.toString()) }
            .subscribe { name -> user.userName = name.toString() }
    }

    private fun getUserEmail(): Disposable {
        return view.inputUserEmail()
            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
            .observeOn(rxSchedulers.androidUI())
            .filter { email -> Validations.emailValidation(email.toString()) }
            .subscribe { email -> user.userEmail = email.toString() }
    }

    private fun getUserPassword(): Disposable {
        return view.inputUserPassword()
            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
            .observeOn(rxSchedulers.androidUI())
            .filter { password -> Validations.passwordValidation(password.toString()) }
            .subscribe { password -> user.userPassword = password.toString() }
    }

    private fun registerUserToFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { p0 ->
            Observable.just(p0)
                .observeOn(rxSchedulers.androidUI())
                .subscribe({
                    if (p0.isSuccessful) {
                        userRepository.savePrimaryUser(user.apply { uid = p0.result!!.user!!.uid })
                            .subscribe {
                                view.showLoginScreen()
                            }.disposeBy(compositeDisposables)
                    }
                    view.showMessage(it.isSuccessful)
                }, {
                    Timber.e(it.localizedMessage)
                })
        }
    }

    fun onCreate() {
        compositeDisposables.addAll(
            onLoginClick(),
            registerUser(),
            getUserName(),
            getUserEmail(),
            getUserPassword()
        )
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    companion object {
        const val THROTTLE_DURATION = 1L
    }
}