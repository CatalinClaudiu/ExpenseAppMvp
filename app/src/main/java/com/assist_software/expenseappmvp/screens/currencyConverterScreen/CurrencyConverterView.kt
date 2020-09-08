package com.assist_software.expenseappmvp.screens.currencyConverterScreen

import android.view.View
import android.widget.AdapterView
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.restModels.response.CurrencyCoin
import com.assist_software.expenseappmvp.screens.currencyConverterScreen.adapter.CurrencyAdapter
import com.assist_software.expenseappmvp.screens.currencyConverterScreen.adapter.models.CurrencyItem
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

    fun initSpinners(currencyObj: CurrencyCoin) {
        val modelList: MutableList<CurrencyItem> = mutableListOf()
        modelList.run {
            add(CurrencyItem("EUR", R.drawable.ic_euro, currencyObj.EUR))
            add(CurrencyItem("USD", R.drawable.ic_dolar, currencyObj.USD))
            add(CurrencyItem("GBP", R.drawable.ic_pound, currencyObj.GBP))
            add(CurrencyItem("CHF", R.drawable.ic_chf, currencyObj.CHF))
            add(CurrencyItem("AUD", R.drawable.ic_aud, currencyObj.AUD))
        }

        val foreignSpinner = CurrencyAdapter(activity, modelList)
        layout.foreign_currency_spinner.adapter = foreignSpinner

        val nativeList: MutableList<CurrencyItem> = mutableListOf()
        nativeList.add(CurrencyItem("RON", R.drawable.ic_flag_ro,1.0))
        val nativeSpinner = CurrencyAdapter(activity, nativeList)
        layout.native_currency_spinner.adapter = nativeSpinner

        layout.foreign_currency_spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    var item: CurrencyItem = parent?.getItemAtPosition(position) as CurrencyItem
                    var valueToConvert = layout.native_currency.text.toString().toDouble()
                    layout.foreign_currency.setText(
                        (layout.native_currency.text.toString()
                            .toDouble() * item.currencyValue).toString()
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}