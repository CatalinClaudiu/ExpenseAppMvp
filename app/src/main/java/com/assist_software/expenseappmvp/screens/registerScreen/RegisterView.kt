package com.assist_software.expenseappmvp.screens.registerScreen

import android.view.View
import android.widget.Toast
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.screens.loginScreen.LoginActivity
import com.assist_software.expenseappmvp.screens.mainScreen.MainActivity
import com.example.spendwithbrain.utils.SharedPrefUtils
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_register.view.*
import java.util.concurrent.TimeUnit

class RegisterView(var activity: RegisterActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_register, null)

    fun showLoginScreen() {
        LoginActivity.start(activity)
        activity.finish()
    }

    fun showMainScreen() {
        MainActivity.start(activity)
        Toast.makeText(activity, SharedPrefUtils.read(Constants.USER_NAME, ""), Toast.LENGTH_SHORT).show()
        activity.finish()
    }

    fun goToLoginScreen(): Observable<Any> {
        return RxView.clicks(layout.login_textView_link)
    }

    fun registerUserClick(): Observable<Any>{
        return RxView.clicks(layout.btn_register)
    }

    fun inputUserName(): Observable<CharSequence>{
        return RxTextView.textChanges(layout.edit_text_name).skipInitialValue()
    }

    fun inputUserEmail(): Observable<CharSequence>{
        return RxTextView.textChanges(layout.edit_text_email).skipInitialValue()
    }

    fun inputUserPassword(): Observable<CharSequence>{
        return RxTextView.textChanges(layout.edit_text_password).skipInitialValue()
    }
}