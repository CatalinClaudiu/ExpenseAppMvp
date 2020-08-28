package com.assist_software.expenseappmvp.screens.registerScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.assist_software.expenseappmvp.application.ExpenseApp
import javax.inject.Inject

class RegisterActivity : Activity() {

    @Inject
    lateinit var presenter: RegisterPresenter

    @Inject
    lateinit var view: RegisterView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerRegisterComponent.builder().appComponent(ExpenseApp.appComponent(this))
            .registerModule(RegisterModule(this)).build().inject(this)

        setContentView(view.layout)
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    companion object {
        fun startRegisterActivity(activity: Activity) {
            val intent = Intent(activity, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
        }
    }
}