package com.assist_software.expenseappmvp.application.builder

import com.assist_software.expenseappmvp.data.restModels.response.BalanceValue
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface RestServiceNotification {
    @GET("balanceArray")
    fun getBalanceById(@Query("id") uid: String): Single<List<BalanceValue>>

    @PUT("balanceArray/{id}")
    fun putBalance(
            @Path("id") id: String,
            @Body balanceValue: BalanceValue): Call<BalanceValue>

    @POST("balanceArray")
    fun postBalance(@Body balanceValue: BalanceValue): Call<BalanceValue>

    @get:GET("balanceArray")
    var getBalance: Observable<BalanceValue>

    @get:GET("balanceArray")
    var getBalance2: Observable<List<BalanceValue>>
}