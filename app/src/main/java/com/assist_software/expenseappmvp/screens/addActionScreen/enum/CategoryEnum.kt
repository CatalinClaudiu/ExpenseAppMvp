package com.assist_software.expenseappmvp.screens.addActionScreen.enum

import android.content.Context
import com.assist_software.expenseappmvp.R

enum class CategoryEnum {
    INCOME {
        override fun getName(context: Context): String {
            return INCOME.name
        }

        override fun getIcon(context: Context): Int {
            return R.drawable.ic_category_income
        }
    },
    FOOD {
        override fun getName(context: Context): String {
            return FOOD.name
        }

        override fun getIcon(context: Context): Int {
            return R.drawable.ic_category_food
        }
    },
    CAR {
        override fun getName(context: Context): String {
            return CAR.name
        }

        override fun getIcon(context: Context): Int {
            return R.drawable.ic_category_car
        }
    },
    CLOTHES {
        override fun getName(context: Context): String {
            return CLOTHES.name
        }

        override fun getIcon(context: Context): Int {
            return R.drawable.ic_category_clothes
        }
    },
    SAVINGS {
        override fun getName(context: Context): String {
            return SAVINGS.name
        }

        override fun getIcon(context: Context): Int {
            return R.drawable.ic_category_savings
        }
    },
    HEALTH {
        override fun getName(context: Context): String {
            return HEALTH.name
        }

        override fun getIcon(context: Context): Int {
            return R.drawable.ic_category_health
        }
    },
    BEAUTY {
        override fun getName(context: Context): String {
            return BEAUTY.name
        }

        override fun getIcon(context: Context): Int {
            return R.drawable.ic_category_beauty
        }
    },
    TRAVEL {
        override fun getName(context: Context): String {
            return TRAVEL.name
        }

        override fun getIcon(context: Context): Int {
            return R.drawable.ic_category_travel
        }
    };

    abstract fun getName(context: Context): String
    abstract fun getIcon(context: Context): Int
}