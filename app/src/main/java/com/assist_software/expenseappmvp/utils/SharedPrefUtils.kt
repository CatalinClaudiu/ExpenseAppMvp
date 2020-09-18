package com.assist_software.expenseappmvp.utils

import android.content.Context
import android.content.SharedPreferences
import com.assist_software.expenseappmvp.data.utils.Constants
import javax.inject.Inject

class SharedPrefUtils @Inject constructor(private val context: Context) {
    private val sharedPref =
        context.getSharedPreferences(Constants.MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)

    fun read(key: String, value: String): String? {
        return sharedPref.getString(key, value)
    }

    fun read(key: String, value: Int): Int {
        return sharedPref.getInt(key, value)
    }

    fun read(key: String, value: Long): Long {
        return sharedPref.getLong(key, value)
    }

    fun write(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun write(key: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun write(key: String, value: Long) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun hasKey(key: String): Boolean {
        return sharedPref.contains(key)
    }

    fun clear() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}