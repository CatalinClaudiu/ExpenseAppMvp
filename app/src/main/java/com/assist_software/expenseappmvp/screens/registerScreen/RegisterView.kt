package com.assist_software.expenseappmvp.screens.registerScreen

import android.view.View
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.loginScreen.LoginActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_register.view.*

class RegisterView(var activity: RegisterActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_register, null)

    fun showLoginScreen() {
        LoginActivity.start(activity)
    }

    fun goToLoginScreen(): Observable<Any> {
        return RxView.clicks(layout.login_textView_link)
    }
}