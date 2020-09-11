package com.assist_software.expenseappmvp.screens.currencyConverterScreen

import android.widget.Toast
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.application.builder.RestServiceInterface
import com.assist_software.expenseappmvp.data.restModels.response.CurrencyCoin
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

class CurrencyConverterPresenter(
    private val view: CurrencyConverterView,
    private val rxSchedulers: RxSchedulers,
    private val converterAPI: RestServiceInterface,
    private val compositeDisposables: CompositeDisposable
) {

    fun onCreate() {
        compositeDisposables.addAll(getCurrency())
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    private fun getCurrency(): Disposable {
        return converterAPI.allCurrency
            .subscribeOn(rxSchedulers.background())
            .observeOn(rxSchedulers.androidUI())
            .doOnError {
                Timber.e(it.localizedMessage)
            }
            .doOnNext { currencyResponse ->
                val currencyObj: CurrencyCoin = currencyResponse?.rates!!
                view.initSpinners(currencyObj)
                view.initMessage(currencyResponse.date)
            }
            .subscribe({
                Timber.d(view.activity.getString(R.string.response_received))
            }, {
                Toast.makeText(
                    view.activity,
                    view.activity.getString(R.string.retrieve_failed),
                    Toast.LENGTH_SHORT
                ).show()
            })
    }
}