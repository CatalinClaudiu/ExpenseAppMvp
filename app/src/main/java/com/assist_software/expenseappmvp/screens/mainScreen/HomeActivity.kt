package com.assist_software.expenseappmvp.screens.mainScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assist_software.expenseappmvp.application.ExpenseApp
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var view: HomeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerHomeComponent.builder().appComponent(ExpenseApp.appComponent(this))
            .homeModule(HomeModule(this)).build().inject(this)

        setContentView(view.layout)
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    companion object {
        fun start(activity: Context) {
            val intent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(intent)
        }
    }
}