package com.assist_software.expenseappmvp.application.builder

import com.assist_software.expenseappmvp.data.utils.rx.AppRxSchedulers
import com.assist_software.expenseappmvp.data.utils.rx.RxBus
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import dagger.Module
import dagger.Provides

@Module
class RxModule {

    @AppScope
    @Provides
    fun provideRxSchedulers(): RxSchedulers {
        return AppRxSchedulers()
    }

    @AppScope
    @Provides
    fun provideRxBus(): RxBus {
        return RxBus()
    }
}
