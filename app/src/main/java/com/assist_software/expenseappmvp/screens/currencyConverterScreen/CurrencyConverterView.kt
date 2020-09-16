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

    fun getTextClick(): Observable<Any> {
        return RxView.clicks(layout.native_currency).skip(1)
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
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    fun calculateRonToValue(amount: Double) {
        val item = layout.foreign_currency_spinner.selectedItem as CurrencyItem
        layout.foreign_currency.setText((amount * item.currencyValue).toString())
    }


    fun calculateValueToRon(amount: Double) {
        val item = layout.foreign_currency_spinner.selectedItem as CurrencyItem
        layout.native_currency.setText((amount / item.currencyValue).toString())
    }

    fun isRonCaseSelected(): Boolean {
        return layout.native_currency.isFocused
    }

    fun isForeignCaseSelected(): Boolean {
        return layout.foreign_currency.isFocused
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

//    fun EditText.updateText(text: String) {
//        val focused = hasFocus()
//        if (focused) {
//            clearFocus()
//        }
//        setText(text)
//        if (focused) {
//            requestFocus()
//        }
//    }
}