package com.assist_software.expenseappmvp.screens.resetPasswordScreen

import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.google.firebase.auth.FirebaseAuth
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ResetPasswordScope

@ResetPasswordScope
@Component(modules = [ResetPasswordModule::class], dependencies = [AppComponent::class])
interface ResetPasswordComponent {
    fun inject(activity: ResetPasswordActivity)
}

@Module
class ResetPasswordModule(private val activity: ResetPasswordActivity) {

    @Provides
    @ResetPasswordScope
    fun view(): ResetPasswordView {
        return ResetPasswordView(activity)
    }

    @Provides
    @ResetPasswordScope
    fun presenter(
        view: ResetPasswordView,
        rxSchedulers: RxSchedulers,
        auth: FirebaseAuth
    ): ResetPasswordPresenter {
        val compositeDisposable = CompositeDisposable()
        return ResetPasswordPresenter(view, rxSchedulers, auth, compositeDisposable)
    }
}