package com.assist_software.expenseappmvp.screens.loginScreen

import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.SharedPrefUtils
import com.google.firebase.auth.FirebaseAuth
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class LoginScope

@LoginScope
@Component(modules = [LoginModule::class], dependencies = [AppComponent::class])
interface LoginComponent {
    fun inject(activity: LoginActivity)
}

@Module
class LoginModule(private val activity: LoginActivity) {

    @Provides
    @LoginScope
    fun view(): LoginView {
        return LoginView(activity)
    }

    @Provides
    @LoginScope
    fun presenter(
        view: LoginView,
        rxSchedulers: RxSchedulers,
        sharedPref: SharedPrefUtils,
        auth: FirebaseAuth,
        userRepository: UserRepository
    ): LoginPresenter {
        val compositeDisposable = CompositeDisposable()
        return LoginPresenter(view, rxSchedulers, userRepository, auth, sharedPref, compositeDisposable)
    }
}