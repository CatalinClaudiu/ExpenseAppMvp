package com.assist_software.expenseappmvp.screens.registerScreen

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.screens.loginScreen.LoginActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_register.view.*

class RegisterView(var activity: RegisterActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_register, null)

    fun showLoginScreen() {
        LoginActivity.start(activity)
        activity.finish()
    }

    fun goToLoginScreen(): Observable<Any> {
        return RxView.clicks(layout.login_textView_link)
    }

    fun registerUserClick(): Observable<Any> {
        return RxView.clicks(layout.btn_register)
    }

    fun inputUserName(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.edit_text_name_register).skipInitialValue()
    }

    fun inputUserEmail(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.edit_text_email_register).skipInitialValue()
    }

    fun inputUserPassword(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.edit_text_password_register).skipInitialValue()
    }

    fun showMessage(isSuccess: Boolean) {
        val message =
            if (isSuccess) activity.resources.getString(R.string.registred_success) else activity.resources.getString(
                R.string.registred_failed
            )
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun fieldValidation(user: User) {
        if (user.userName == "") {
            layout.text_input_name_register.setBackgroundColor(Color.RED)
        } else {
            layout.text_input_name_register.setBackgroundColor(Color.WHITE)
        }
        if (user.userEmail == "") {
            layout.text_input_email_register.setBackgroundColor(Color.RED)
        } else {
            layout.text_input_email_register.setBackgroundColor(Color.WHITE)
        }
        if (user.userPassword == "") {
            layout.text_input_password_register.setBackgroundColor(Color.RED)
        } else {
            layout.text_input_password_register.setBackgroundColor(Color.WHITE)
        }
    }
}