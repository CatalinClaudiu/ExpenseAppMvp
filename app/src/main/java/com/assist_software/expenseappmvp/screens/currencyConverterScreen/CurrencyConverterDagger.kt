package com.assist_software.expenseappmvp.screens.currencyConverterScreen

import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.application.builder.NetworkModule
import com.assist_software.expenseappmvp.application.builder.RestServiceInterface
import com.assist_software.expenseappmvp.application.builder.RestServiceModule
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.screens.loginScreen.*
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import com.google.firebase.auth.FirebaseAuth
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CurrencyConverterScope

@CurrencyConverterScope
@Component(modules = [CurrencyConverterModule::class], dependencies = [AppComponent::class])
interface CurrencyConverterComponent {
    fun inject(activity: CurrencyConverterActivity)
}

@Module
class CurrencyConverterModule(private val activity: CurrencyConverterActivity) {

    @Provides
    @CurrencyConverterScope
    fun view(): CurrencyConverterView {
        return CurrencyConverterView(activity)
    }

    @Provides
    @CurrencyConverterScope
    fun presenter(
        view: CurrencyConverterView,
        rxSchedulers: RxSchedulers,
        sharedPref: SharedPrefUtils,
        auth: FirebaseAuth,
        restServiceInterface: RestServiceInterface,
        userRepository: UserRepository
    ): CurrencyConverterPresenter {
        val compositeDisposable = CompositeDisposable()
        return CurrencyConverterPresenter(view, rxSchedulers, userRepository, auth, restServiceInterface, sharedPref, compositeDisposable)
    }
}