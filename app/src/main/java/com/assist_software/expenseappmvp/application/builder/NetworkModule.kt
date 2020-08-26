package com.assist_software.expenseappmvp.application.builder

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import javax.inject.Named

@Module
class NetworkModule {

    @AppScope
    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        RxJavaPlugins.setErrorHandler { e -> Timber.d(e) }
        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    }

    @AppScope
    @Provides
    fun cache(context: Context, @Named("OkHttpCacheDir") cacheDir: String, @Named("OkHttpCacheSize") cacheSize: Int): Cache {
        return Cache(File(context.filesDir, cacheDir), cacheSize.toLong())
    }

    @AppScope
    @Provides
    @Named("OkHttpCacheDir")
    fun cacheDir(): String {
        return "OkHttpCache"
    }

    @AppScope
    @Provides
    @Named("OkHttpCacheSize")
    fun cacheSize(): Int {
        return 10 * 1024 * 1024 //10MB cache
    }

    @AppScope
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Timber.i(message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}