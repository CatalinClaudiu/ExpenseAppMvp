package com.assist_software.expenseappmvp.screens.addActionScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assist_software.expenseappmvp.application.ExpenseApp
import com.assist_software.expenseappmvp.screens.mainScreen.fragments.expense.adapter.models.Transaction
import com.google.gson.Gson
import javax.inject.Inject

class AddActionActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: AddActionPresenter

    @Inject
    lateinit var view: AddActionView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAddActionComponent.builder().appComponent(ExpenseApp.appComponent(this))
            .addActionModule(AddActionModule(this)).build().inject(this)

        setContentView(view.layout)
        getBundleContent()
        view.initToolbar()
        presenter.onCreate()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        view.loadImageInView(requestCode, resultCode, data)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun getBundleContent() {
        val transaction = intent.getStringExtra("transaction")
        if (transaction != null)
            presenter.transaction = Gson().fromJson(transaction, Transaction::class.java)
    }

    companion object {
        fun start(activity: Context) {
            val intent = Intent(activity, AddActionActivity::class.java)
            activity.startActivity(intent)
        }
    }
}