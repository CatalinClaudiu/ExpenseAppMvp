package com.assist_software.expenseappmvp.application.builder

import com.assist_software.expenseappmvp.data.restModels.response.CurrencyResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RestServiceInterface {
    @get:GET("latest?base=RON")
    val allCurrency: Observable<CurrencyResponse>
}