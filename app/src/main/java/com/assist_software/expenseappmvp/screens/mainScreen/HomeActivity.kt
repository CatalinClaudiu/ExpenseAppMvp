package com.assist_software.expenseappmvp.screens.mainScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.assist_software.expenseappmvp.application.ExpenseApp
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.home_toolbar.view.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var view: HomeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerHomeComponent.builder().appComponent(ExpenseApp.appComponent(this))
            .homeModule(HomeModule(this))
            .build().inject(this)

        setContentView(view.layout)
        view.initComponents()
        view.initToolbar()
        view.initNavigationDrawer(view.layout.home_tool_bar)
        presenter.onCreate()
    }

    override fun onBackPressed() {
        if (view.layout.home_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            view.layout.home_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            finish()
        }
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