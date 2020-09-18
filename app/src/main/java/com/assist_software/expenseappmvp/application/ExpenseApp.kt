package com.assist_software.expenseappmvp.application

import android.app.*
import android.content.Context
import android.os.Build
import com.assist_software.expenseappmvp.BuildConfig
import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.application.builder.AppModule
import com.assist_software.expenseappmvp.application.builder.DaggerAppComponent
import com.assist_software.expenseappmvp.data.utils.Constants
import com.facebook.stetho.Stetho
import dagger.android.*
import timber.log.Timber
import javax.inject.Inject


class ExpenseApp : Application(), HasServiceInjector, HasActivityInjector, HasAndroidInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun serviceInjector(): AndroidInjector<Service> = dispatchingServiceInjector

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun onCreate() {
        super.onCreate()
        instance = this
        Stetho.initializeWithDefaults(this)
        initComponent()
        initTimber()
        createNotificationChannel()
        appComponent?.inject(this)
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                Constants.CHANNEL_ID,
                "Example Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
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