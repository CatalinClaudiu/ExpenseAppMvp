package com.assist_software.expenseappmvp.screens.splashScreen

import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SplashScope

@SplashScope
@Component(modules = [SplashModule::class], dependencies = [AppComponent::class])
interface SplashComponent {
    fun inject(activity: SplashActivity)
}

@Module
class SplashModule(private val activity: SplashActivity) {

    @Provides
    @SplashScope
    fun view(): SplashView {
        return SplashView(activity)
    }

    @Provides
    @SplashScope
    fun presenter(view: SplashView, rxSchedulers: RxSchedulers, userRepository: UserRepository, asharedPref: SharedPrefUtils): SplashPresenter {
        val compositeDisposable = CompositeDisposable()
        return SplashPresenter(view, rxSchedulers, asharedPref, compositeDisposable)
    }
}