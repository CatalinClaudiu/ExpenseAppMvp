package com.assist_software.expenseappmvp.screens.loginScreen

import android.view.View
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.mainScreen.MainActivity
import com.assist_software.expenseappmvp.screens.registerScreen.RegisterActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_login.view.edit_text_email
import kotlinx.android.synthetic.main.activity_login.view.edit_text_password
import kotlinx.android.synthetic.main.activity_register.view.*

class LoginView(var activity: LoginActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_login, null)

    fun showRegisterScreen() {
        RegisterActivity.startRegisterActivity(activity)
        activity.finish()
    }

    fun goToRegisterScreen(): Observable<Any> {
        return RxView.clicks(layout.register_textView_link)
    }

    fun showMainScreen(){
        MainActivity.start(activity)
        activity.finish()
    }

    fun loginUserClicks(): Observable<Any>{
        return RxView.clicks(layout.btn_login)
    }

    fun inputUserEmail(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.edit_text_email).skipInitialValue()
    }

    fun inputUserPassword(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.edit_text_password).skipInitialValue()
    }
}