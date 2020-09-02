package com.assist_software.expenseappmvp.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun String.toUpperCaseTest(): String {
    return this.toUpperCase()
}

fun Disposable.disposeBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}