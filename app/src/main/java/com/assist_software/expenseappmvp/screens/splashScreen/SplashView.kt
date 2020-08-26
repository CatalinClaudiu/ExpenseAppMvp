package com.assist_software.expenseappmvp.screens.splashScreen

import android.view.View
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.loginScreen.LoginActivity
import com.assist_software.expenseappmvp.screens.mainScreen.MainActivity

class SplashView(var activity: SplashActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_splash, null)

    fun showMainScreen() {
        MainActivity.start(activity)
    }

    fun showLoginScreen(){
        LoginActivity.start(activity)
    }
}