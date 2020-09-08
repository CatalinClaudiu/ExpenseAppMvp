package com.assist_software.expenseappmvp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getOneMonthAgoTime(timestamp: Long): Long {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        return calendar.timeInMillis
    }

    @SuppressLint("SimpleDateFormat")
    fun getMonthName(startDate: Long): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            timeInMillis = startDate
        }
        return SimpleDateFormat("MMM").format(calendar.time)
    }

    fun getEndDate(param: Int): Long {
        val c1: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        c1.add(param, -1)
        return c1.timeInMillis
    }
}