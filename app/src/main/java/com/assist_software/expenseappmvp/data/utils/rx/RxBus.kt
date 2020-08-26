package com.assist_software.expenseappmvp.data.utils.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {

    private val bus = PublishSubject.create<Any>().toSerialized()

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun toObserverable(): Observable<Any> {
        return bus
    }
}