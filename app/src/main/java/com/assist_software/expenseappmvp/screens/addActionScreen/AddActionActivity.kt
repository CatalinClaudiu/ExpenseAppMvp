package com.assist_software.expenseappmvp.screens.addActionScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assist_software.expenseappmvp.R

class AddActionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_action)
    }

    companion object {
        fun start(activity: Context) {
            val intent = Intent(activity, AddActionActivity::class.java)
            activity.startActivity(intent)
        }
    }
}