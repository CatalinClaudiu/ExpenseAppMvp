package com.assist_software.expenseappmvp.utils

import android.annotation.SuppressLint
import android.content.Context
import com.assist_software.expenseappmvp.R
import com.assist_software.expenseappmvp.data.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    @SuppressLint("SimpleDateFormat")
    fun gmtToLocalTime(timestamp: Long): String {
        val formatter = SimpleDateFormat("yyyy/MM/dd' at 'HH:mm")
        return formatter.format(Date(timestamp))
    }

    @SuppressLint("SimpleDateFormat")
    fun transactionTimeFormat(context: Context, timestamp: Long): String {
        val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val timeMs = calendar.timeInMillis

        return if (timeMs - timestamp < Constants.oneDayMs) {
            context.getString(R.string.today)
        } else if (timeMs - timestamp > Constants.oneDayMs && timeMs - timestamp < Constants.twoDayMs) {
            context.getString(R.string.yesterday)
        } else {
            val formatter = SimpleDateFormat("EEE dd MMM")
            formatter.format(Date(timestamp))
        }
    }
}