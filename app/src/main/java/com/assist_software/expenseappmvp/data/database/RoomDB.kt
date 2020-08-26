package com.assist_software.expenseappmvp.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class RoomDB(context: Context) {

    val appDatabase: AppDatabase

    init {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .allowMainThreadQueries()
                .build()
    }

    companion object {
        private const val DATABASE_NAME = "my-database"
    }
}
