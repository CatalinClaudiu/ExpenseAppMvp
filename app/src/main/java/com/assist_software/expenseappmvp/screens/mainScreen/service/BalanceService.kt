package com.assist_software.expenseappmvp.screens.mainScreen.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.application.builder.RestServiceNotification
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.AppRxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import com.assist_software.expenseappmvp.utils.disposeBy
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BalanceService @Inject constructor() : Service() {

    @Inject
    lateinit var rxSchedulers: AppRxSchedulers

    @Inject
    lateinit var sharedPreferences: SharedPrefUtils

    @Inject
    lateinit var resetServiceNotification: RestServiceNotification

    @Inject
    lateinit var userRepository: UserRepository

    val compositeDisposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        observeServer(intent)

        return START_NOT_STICKY
    }

    private fun observeServer(intent: Intent?) {
        var currentBalance = 0.0
        var uid = ""
        if (sharedPreferences.hasKey(Constants.USER_ID)) {
            uid = sharedPreferences.read(Constants.USER_ID, "").toString()
        }
        Observable.interval(
            3,
            TimeUnit.SECONDS
        )
            .observeOn(rxSchedulers.background())
            .flatMap { userRepository.getUserBalance(uid).toObservable() }
            .flatMap {
                currentBalance = it
                resetServiceNotification.getBalanceById(uid).toObservable()
            }
            .observeOn(rxSchedulers.androidUI())
            .subscribe({
                if(currentBalance != it.first().balance.toDouble()){
                    showBalanceNotification(it.first().balance, intent)
                }
            }, {
                it.localizedMessage
            })
            .disposeBy(compositeDisposable)
    }

    private fun showBalanceNotification(balance: Int, intent: Intent?) {
        val input = intent?.getStringExtra("inputExtra")

        val pendingIntent = PendingIntent.getActivity(
            this,
            0, intent, 0
        )

        val notification = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_msg, balance.toString()))
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}