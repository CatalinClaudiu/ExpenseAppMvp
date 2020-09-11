package com.assist_software.expenseappmvp.screens.registerScreen

import com.assist_software.expenseappmvp.application.builder.AppComponent
import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.google.firebase.auth.FirebaseAuth
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class RegisterScope

@RegisterScope
@Component(
    modules = [RegisterModule::class],
    dependencies = [AppComponent::class]
)
interface RegisterComponent {
    fun inject(activity: RegisterActivity)
}

@Module
class RegisterModule(private val activity: RegisterActivity) {

    @Provides
    @RegisterScope
    fun view(): RegisterView {
        return RegisterView(activity)
    }

    @Provides
    @RegisterScope
    fun presenter(
        view: RegisterView,
        rxSchedulers: RxSchedulers,
        userRepository: UserRepository,
        auth: FirebaseAuth
    ): RegisterPresenter {
        val compositeDisposable = CompositeDisposable()
        return RegisterPresenter(
            view,
            rxSchedulers,
            userRepository,
            auth,
            compositeDisposable
        )
    }
}