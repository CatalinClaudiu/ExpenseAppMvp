package com.assist_software.expenseappmvp.screens.currencyConverterScreen

import android.widget.Toast
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.application.builder.RestServiceInterface
import com.assist_software.expenseappmvp.data.restModels.response.CurrencyResponse
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CurrencyConverterPresenter(
    private val view: CurrencyConverterView,
    private val rxSchedulers: RxSchedulers,
    private val converterAPI: RestServiceInterface,
    private val compositeDisposables: CompositeDisposable
) {

    private var currencyObj: CurrencyResponse? = null

    fun onCreate() {
        compositeDisposables.addAll(getCurrency(), convertCurrencyRonToValue()/*, convertCurrencyValueToRon(), nativeEditTextClick()*/)
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    private fun convertCurrencyRonToValue(): Disposable {
        return view.getCurrencyRonToValue()
            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
            .observeOn(rxSchedulers.androidUI())
            .subscribe { view.calculateRonToValue() }
    }

    private fun convertCurrencyValueToRon(): Disposable {
        return view.getCurrencyValueToRon()
            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
            .observeOn(rxSchedulers.androidUI())
            .subscribe { view.calculateValueToRon() }
    }

//    private fun nativeEditTextClick(): Disposable{
//        return view.setNativeEditTextFocus()
//            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
//            .observeOn(rxSchedulers.androidUI())
//            .subscribe{view.focusChange(true)}
//    }
//
//    private fun foreignEditTextClick(): Disposable{
//        return view.setForeignEditTextFocus()
//            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
//            .observeOn(rxSchedulers.androidUI())
//            .subscribe{view.focusChange(false)}
//    }

    private fun getCurrency(): Disposable {
        return converterAPI.allCurrency
            .subscribeOn(rxSchedulers.background())
            .observeOn(rxSchedulers.androidUI())
            .doOnError {
                Timber.e(it.localizedMessage)
            }
            .doOnNext { currencyResponse ->
                currencyObj = currencyResponse
                view.initSpinners(currencyObj?.rates)
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