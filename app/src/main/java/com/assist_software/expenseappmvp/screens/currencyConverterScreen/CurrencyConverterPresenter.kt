package com.assist_software.expenseappmvp.screens.currencyConverterScreen

import android.text.TextUtils.isDigitsOnly
import android.widget.Toast
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.application.builder.RestServiceInterface
import com.assist_software.expenseappmvp.data.restModels.response.CurrencyResponse
import com.assist_software.expenseappmvp.data.utils.Constants
import com.assist_software.expenseappmvp.data.utils.rx.RxSchedulers
import com.assist_software.expenseappmvp.utils.disposeBy
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
        compositeDisposables.addAll(getCurrency(), textClick())
        convertCurrencyValueToRon()
        convertCurrencyRonToValue()
    }

    fun onDestroy() {
        compositeDisposables.clear()
    }

    private fun convertCurrencyRonToValue() {
        view.getCurrencyRonToValue()
            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
            .observeOn(rxSchedulers.androidUI())
            .subscribe {
                if (isDigitsOnly(it) && !view.isForeignCaseSelected() && it.isNotEmpty())
                    view.calculateRonToValue(it.toString().toDouble())
            }.disposeBy(compositeDisposables)
    }

    private fun convertCurrencyValueToRon() {
        view.getCurrencyValueToRon()
            .debounce(Constants.AWAIT_INPUT, TimeUnit.MILLISECONDS)
            .observeOn(rxSchedulers.androidUI())
            .subscribe {
                if (isDigitsOnly(it) && !view.isRonCaseSelected() && it.isNotEmpty())
                    view.calculateValueToRon(it.toString().toDouble())
            }.disposeBy(compositeDisposables)
    }

    private fun textClick(): Disposable {
        return view.getTextClick()
            .throttleFirst(Constants.THROTTLE_DURATION, TimeUnit.SECONDS)
            .observeOn(rxSchedulers.androidUI())
            .subscribe {
                Toast.makeText(view.activity, "Click", Toast.LENGTH_SHORT).show()
            }
    }

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