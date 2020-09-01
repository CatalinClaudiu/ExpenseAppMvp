package com.assist_software.expenseappmvp.application

import android.app.Application
import android.content.Context
import com.assist_software.expenseappmvp.BuildConfig
import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.application.builder.AppModule
import com.assist_software.expenseappmvp.application.builder.DaggerAppComponent
import timber.log.Timber

class ExpenseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initComponent()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun getAppComponent(): AppComponent {
        appComponent?.let {
            return it
        } ?: kotlin.run {
            return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        }
    }

    private fun initComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        @get:Synchronized
        var instance: ExpenseApp? = null
            private set
        var appComponent: AppComponent? = null

        fun appComponent(context: Context): AppComponent {
            return (context.applicationContext as ExpenseApp).getAppComponent()
        }
    }
}