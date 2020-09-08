package com.assist_software.expenseappmvp.data.database.repositories

import com.assist_software.expenseappmvp.data.database.AppDatabase
import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.data.database.models.UserWithExpenses
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

class UserRepository(private val db: AppDatabase) {

    fun savePrimaryUser(user: User): Flowable<Unit> {
        return Flowable.just(db.userDao().saveUser(user))
    }

    fun getUserId(email: String): Single<String> {
        return db.userDao().getUserIdByEmail(email)
    }

    fun getUserBalance(uid: String): Maybe<Double> {
        return db.userDao().getUserBalance(uid)
    }

    fun getUserName(uid: String): Single<String>{
        return db.userDao().getUserName(uid)
    }

    fun loadUserWithExpenses(uid: String): UserWithExpenses{
        return db.userDao().getUserExpenses(uid)
    }
}