package com.assist_software.expenseappmvp.data.database.dao

import androidx.room.*
import com.assist_software.expenseappmvp.data.database.entities.User
import com.assist_software.expenseappmvp.data.database.models.UserWithExpenses
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE uid = :id ")
    fun getUserById(id: String): Flowable<User>

    @Query("SELECT * FROM users WHERE userEmail = :email")
    fun getUserByEmail(email: String): Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

    @Query("SELECT uid FROM users WHERE userEmail = :email")
    fun getUserIdByEmail(email: String): Single<String>

    @Query("SELECT userCurrentBalance FROM users WHERE uid = :uid")
    fun getUserBalance(uid: String): Maybe<Double>

    @Query("SELECT userName FROM users WHERE uid = :uid")
    fun getUserName(uid: String): Single<String>

    @Transaction
    @Query("SELECT * FROM users WHERE uid=:uid")
    fun getUserExpenses(uid: String): UserWithExpenses
}