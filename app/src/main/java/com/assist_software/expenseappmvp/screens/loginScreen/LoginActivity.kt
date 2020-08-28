package com.assist_software.expenseappmvp.screens.loginScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assist_software.expenseappmvp.application.ExpenseApp
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: LoginPresenter

    @Inject
    lateinit var view: LoginView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerLoginComponent.builder().appComponent(ExpenseApp.appComponent(this))
            .loginModule(LoginModule(this)).build().inject(this)

        setContentView(view.layout)
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    companion object {
        fun start(activity: Context) {
            val intent = Intent(activity, LoginActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }
}