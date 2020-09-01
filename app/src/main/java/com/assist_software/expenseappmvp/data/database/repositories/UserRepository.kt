package com.assist_software.expenseappmvp.data.database.repositories

import com.assist_software.expenseappmvp.data.database.AppDatabase
import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.data.utils.Constants
import io.reactivex.Flowable

class UserRepository(private val db: AppDatabase) {

    fun savePrimaryUser(user: User):Flowable<Unit> {
        return Flowable.just(db.userDao().saveUser(user))
    }

    fun getUser(id: Long): Flowable<Any> {
        return db.userDao().getUserById(id)
                .flatMap {
                    if (it.isEmpty()) {
                        Flowable.just(Constants.EMPTY)
                    } else {
                        Flowable.just(it.first())
                    }
                }
    }

    fun removeUser(id: Long): Flowable<Any> {
        return Flowable.just(db.userDao().deleteUser(id))
    }

    fun getUserId(email: String): Long{
        return db.userDao().getUserIdByEmail(email)
    }
}