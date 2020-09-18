package com.assist_software.expenseappmvp.application.builder

import com.assist_software.expenseappmvp.screens.mainScreen.service.BalanceService
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class ServiceModule {
    @ContributesAndroidInjector
    abstract fun contributeMyService(): BalanceService?
}