package com.assist_software.expenseappmvp.utils

import android.util.Patterns
import java.util.regex.Pattern

object Validations {
    fun emailValidation(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun passwordValidation(password: String): Boolean {
        return password.isNotEmpty() && Pattern.matches(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!.*()_~])(?=\\S+$).{6,}$",
            password
        )
    }

    fun nameValidation(name: String): Boolean {
        return name.isNotEmpty() && name.split(" ").size >= 2 && !Pattern.matches(
            "/^[a-zA-Z\\s]*$/",
            name
        )
    }
}