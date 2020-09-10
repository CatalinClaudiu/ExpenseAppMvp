package com.assist_software.expenseappmvp.screens.resetPasswordScreen

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.screens.loginScreen.LoginActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_reset_password.view.*

class ResetPasswordView(var activity: ResetPasswordActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_reset_password, null)

    fun resetPasswordClick(): Observable<Any> {
        return RxView.clicks(layout.reset_password_btn)
    }

    fun showLoginScreen() {
        LoginActivity.start(activity)
        activity.finish()
    }

    fun inputUserEmail(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.edit_text_email_reset_password).skipInitialValue()
    }

    fun showMessage(isSuccess: Boolean) {
        val message =
            if (isSuccess) activity.resources.getString(R.string.password_reset_success) else activity.resources.getString(
                R.string.password_reset_failed
            )
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun fieldValidation(user: User) {
        if (user.userEmail == "") {
            layout.text_input_email_reset_password.setBackgroundColor(Color.RED)
        } else {
            layout.text_input_email_reset_password.setBackgroundColor(Color.WHITE)
        }
    }
}