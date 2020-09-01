package com.assist_software.expenseappmvp.screens.registerScreen

import android.graphics.Color
import android.widget.Toast
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import com.assist_software.expenseappmvp.utils.Validations
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_register.view.*
import timber.log.Timber
import java.util.concurrent.TimeUnit


class RegisterPresenter(
    private val view: RegisterView,
    private val rxSchedulers: RxSchedulers,
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val sharedPref: SharedPrefUtils,
    private val compositeDisposables: CompositeDisposable
) {
    private val user: User = User()

    fun onCreate() {
        compositeDisposables.addAll(
            onLoginClick(),
            registerUser(),
            getUserName(),
            getUserEmail(),
            getUserPassword()
        )

        val user = auth.currentUser
        Timber.e("${user?.email}")
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
        return view.registerUserClick()
            .observeOn(rxSchedulers.androidUI())
            .doOnNext { fieldValidation() }
            .filter {
                !(user.userName == "" || user.userEmail == "" || user.userPassword == "")
            }
            .doOnNext {
                sharedPref.write(Constants.USER_NAME, user.userName)
                userRepository.savePrimaryUser(user)
                registerUserToFirebase(user.userEmail, user.userPassword)
            }
            .subscribe({
                view.showLoginScreen()
            }, {
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
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {
                    Toast.makeText(
                        view.activity,
                        view.activity.getString(R.string.registred_success),
                        Toast.LENGTH_LONG
                    )
                        .show()

                    auth.signOut()
                } else {
                    Timber.e(p0.result.toString())
                    Toast.makeText(
                        view.activity,
                        view.activity.getString(R.string.registred_failed),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun fieldValidation() {
        if (user.userName == "") {
            view.layout.text_input_name.setBackgroundColor(Color.RED)
        } else {
            view.layout.text_input_name.setBackgroundColor(Color.WHITE)
        }
        if (user.userEmail == "") {
            view.layout.text_input_email.setBackgroundColor(Color.RED)
        } else {
            view.layout.text_input_email.setBackgroundColor(Color.WHITE)
        }
        if (user.userPassword == "") {
            view.layout.text_input_password.setBackgroundColor(Color.RED)
        } else {
            view.layout.text_input_password.setBackgroundColor(Color.WHITE)
        }
    }

    companion object {
        const val THROTTLE_DURATION = 1L
    }
}