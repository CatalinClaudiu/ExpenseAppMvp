package com.assist_software.expenseappmvp.application.builder

import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
class GsonModule {

    @AppScope
    @Provides
    fun gson(): Gson {
        val builder = GsonBuilder()
        Converters.registerAll(builder)
        return builder.create()
    }
}
