package com.assist_software.expenseappmvp.application.builder

import android.content.Context
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import dagger.Module
import dagger.Provides

@Module
class SharedPrefModule {
    @AppScope
    @Provides
    fun initSharedPref(context: Context): SharedPrefUtils {
        return SharedPrefUtils(context)
    }
}