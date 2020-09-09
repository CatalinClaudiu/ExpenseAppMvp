package com.assist_software.expenseappmvp.data.restModels.response

data class CurrencyResponse(val isSuccess: Boolean, val base: String, val date: String, val rates: CurrencyCoin)