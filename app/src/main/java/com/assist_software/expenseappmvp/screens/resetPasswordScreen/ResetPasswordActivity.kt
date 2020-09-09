package com.assist_software.expenseappmvp.screens.resetPasswordScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.application.ExpenseApp
import com.assist_software.expenseappmvp.screens.loginScreen.DaggerLoginComponent
import com.assist_software.expenseappmvp.screens.loginScreen.LoginModule
import com.assist_software.expenseappmvp.screens.loginScreen.LoginPresenter
import com.assist_software.expenseappmvp.screens.loginScreen.LoginView
import javax.inject.Inject

class ResetPasswordActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: ResetPasswordPresenter

    @Inject
    lateinit var view: ResetPasswordView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerResetPasswordComponent.builder().appComponent(ExpenseApp.appComponent(this))
            .resetPasswordModule(ResetPasswordModule(this)).build().inject(this)

        setContentView(view.layout)
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, ResetPasswordActivity::class.java)
            activity.startActivity(intent)
        }
    }
}