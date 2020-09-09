package com.assist_software.expenseappmvp.screens.loginScreen

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.screens.mainScreen.HomeActivity
import com.assist_software.expenseappmvp.screens.registerScreen.RegisterActivity
import com.assist_software.expenseappmvp.screens.resetPasswordScreen.ResetPasswordActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
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

    fun showResetPasswordScreen(){
        ResetPasswordActivity.start(activity)
    }

    fun goToResetPasswordScreen(): Observable<Any>{
        return RxView.clicks(layout.forgot_password)
    }

    fun showMainScreen() {
        HomeActivity.start(activity)
        activity.finish()
    }

    fun loginUserClicks(): Observable<Any> {
        return RxView.clicks(layout.btn_login)
    }

    fun inputUserEmail(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.edit_text_email_login).skipInitialValue()
    }

    fun inputUserPassword(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.edit_text_password_login).skipInitialValue()
    }

    fun showMessage(isSuccess: Boolean){
        val message =
            if (isSuccess) activity.resources.getString(R.string.login_success) else activity.resources.getString(
                R.string.login_failed
            )
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun fieldValidation(user: User) {
        if (user.userEmail == "") {
            layout.text_input_email_login.setBackgroundColor(Color.RED)
        } else {
            layout.text_input_email_login.setBackgroundColor(Color.WHITE)
        }
        if (user.userPassword == "") {
            layout.text_input_password_login.setBackgroundColor(Color.RED)
        } else {
            layout.text_input_password_login.setBackgroundColor(Color.WHITE)
        }
    }
}