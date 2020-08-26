package com.assist_software.expenseappmvp.screens.splashScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assist_software.expenseappmvp.application.ExpenseApp
import javax.inject.Inject

class SplashActivity: AppCompatActivity() {

    @Inject
    lateinit var presenter: SplashPresenter

    @Inject
    lateinit var view: SplashView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerSplashComponent.builder().appComponent(ExpenseApp.appComponent(this))
                .splashModule(SplashModule(this)).build().inject(this)
        setContentView(view.layout)
        presenter.onCreate()
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}