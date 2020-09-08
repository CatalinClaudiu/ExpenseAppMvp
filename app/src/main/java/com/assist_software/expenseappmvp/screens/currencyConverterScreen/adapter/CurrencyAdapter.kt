package com.assist_software.expenseappmvp.screens.currencyConverterScreen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.screens.currencyConverterScreen.adapter.models.CurrencyItem

class CurrencyAdapter(val context: Context, private var currencyList: List<CurrencyItem>) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.currency_spinner_row, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = currencyList[position].currencyName
        vh.img.setBackgroundResource(currencyList[position].currencyIcon)

        return view
    }

    override fun getItem(position: Int): Any {
        return currencyList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return currencyList.size
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.currency_coin) as TextView
        val img: ImageView = row?.findViewById(R.id.imageView_flag) as ImageView

    }
}