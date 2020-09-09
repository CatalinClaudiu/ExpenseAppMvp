package com.assist_software.expenseappmvp.screens.currencyConverterScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assist_software.expenseappmvp.application.ExpenseApp
import javax.inject.Inject

class CurrencyConverterActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: CurrencyConverterPresenter

    @Inject
    lateinit var view: CurrencyConverterView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerCurrencyConverterComponent.builder().appComponent(ExpenseApp.appComponent(this))
            .currencyConverterModule(CurrencyConverterModule(this)).build().inject(this)

        setContentView(view.layout)
        view.iniToolbar()
        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        fun start(activity: Context) {
            val intent = Intent(activity, CurrencyConverterActivity::class.java)
            activity.startActivity(intent)
        }
    }
}