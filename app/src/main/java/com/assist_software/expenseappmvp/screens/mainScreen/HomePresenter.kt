package com.assist_software.expenseappmvp.screens.mainScreen

import com.assist_software.expenseappmvp.data.database.repositories.UserRepository
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class HomePresenter(
    private val view: HomeView,
    private val rxSchedulers: RxSchedulers,
    private val userRepository: UserRepository,
    private val compositeDisposables: CompositeDisposable
) {

    private fun onAddActionClick(): Disposable {
        return view.goToAddActionScreen()
            .subscribe {
                view.showAddActionScreen()
            }
    }

    fun onCreate() {
        compositeDisposables.addAll(onAddActionClick()/*, onViewPagerSwipe()*/)
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }
}