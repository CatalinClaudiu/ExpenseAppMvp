package com.assist_software.expenseappmvp.screens.loginScreen

import android.view.View
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.registerScreen.RegisterActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginView(var activity: LoginActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_login, null)

    fun showRegisterScreen() {
        RegisterActivity.startRegisterActivity(activity)
        activity.finish()
    }

    fun goToRegisterScreen(): Observable<Any> {
        return RxView.clicks(layout.register_textView_link)
    }
}