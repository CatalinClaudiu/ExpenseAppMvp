package com.assist_software.expenseappmvp.screens.currencyConverterScreen

import android.view.View
import android.widget.AdapterView
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.restModels.response.CurrencyCoin
import com.assist_software.expenseappmvp.screens.currencyConverterScreen.adapter.CurrencyAdapter
import com.assist_software.expenseappmvp.screens.currencyConverterScreen.adapter.models.CurrencyItem
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_currency_converter.view.*
import kotlinx.android.synthetic.main.home_toolbar.view.*

class CurrencyConverterView(var activity: CurrencyConverterActivity) {
    val layout: View = View.inflate(activity, R.layout.activity_currency_converter, null)
//    var hasFocus = true
//
//    fun focusChange(param: Boolean){
//        hasFocus = param
//    }

    fun iniToolbar() {
        var toolbar = layout.home_tool_bar
        activity.run {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
        toolbar.run {
            title = activity.getString(R.string.converter)
            setTitleTextAppearance(layout.context, R.style.HomeToolbarFont)
        }

        toolbar.button_add_action.visibility = View.GONE
    }

    fun initMessage(date: String) {
        layout.converted_using_api_textView.text = activity.getString(R.string.dodgy_api_date, date)
    }

    fun getCurrencyRonToValue(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.native_currency).skipInitialValue()
    }

    fun getCurrencyValueToRon(): Observable<CharSequence> {
        return RxTextView.textChanges(layout.foreign_currency).skipInitialValue()
    }

    fun setNativeEditTextFocus(): Observable<Any> {
        return RxView.clicks(layout.native_currency)
    }

    fun setForeignEditTextFocus(): Observable<Any> {
        return RxView.clicks(layout.foreign_currency)
    }

    fun initSpinners(currencyObj: CurrencyCoin?) {
        if (currencyObj == null) return
        initForeignSpinner(currencyObj)
        initNativeSpinner()
        layout.foreign_currency_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position) as CurrencyItem
//                if(hasFocus){
                    calculateRonToValue()
//                }
//                else{
//                    calculateValueToRon()
//                }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    fun calculateRonToValue() {
        val item = layout.foreign_currency_spinner.selectedItem as CurrencyItem
        if (layout.native_currency.text.isNotEmpty()) {
            var valueToConvert = layout.native_currency.text.toString().toDouble()
            layout.foreign_currency.setText((valueToConvert * item.currencyValue).toString())
        }
    }

    fun calculateValueToRon() {
        val item = layout.foreign_currency_spinner.selectedItem as CurrencyItem
        if (layout.foreign_currency.text.isNotEmpty()) {
            var valueToConvert = layout.foreign_currency.text.toString().toDouble()
            layout.native_currency.setText((valueToConvert / item.currencyValue).toString())
        }
    }

    private fun initForeignSpinner(currencyObj: CurrencyCoin) {
        val modelList: MutableList<CurrencyItem> = mutableListOf()
        modelList.run {
            add(CurrencyItem("Currency", 0, 0.0))
            add(CurrencyItem("EUR", R.drawable.ic_euro, currencyObj.EUR))
            add(CurrencyItem("USD", R.drawable.ic_dolar, currencyObj.USD))
            add(CurrencyItem("GBP", R.drawable.ic_pound, currencyObj.GBP))
            add(CurrencyItem("CHF", R.drawable.ic_chf, currencyObj.CHF))
            add(CurrencyItem("AUD", R.drawable.ic_aud, currencyObj.AUD))
        }

        val foreignSpinner = CurrencyAdapter(activity, modelList)
        layout.foreign_currency_spinner.adapter = foreignSpinner
    }

    private fun initNativeSpinner() {
        val nativeList: MutableList<CurrencyItem> = mutableListOf()
        nativeList.add(CurrencyItem("RON", R.drawable.ic_flag_ro, 1.0))
        val nativeSpinner = CurrencyAdapter(activity, nativeList)
        layout.native_currency_spinner.adapter = nativeSpinner
    }
}