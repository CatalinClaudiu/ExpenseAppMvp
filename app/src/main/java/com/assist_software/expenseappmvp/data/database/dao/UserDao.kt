package com.assist_software.expenseappmvp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assist_software.expenseappmvp.data.database.entities.User
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE userId = :id LIMIT 1")
    fun getUserById(id: Long = 0): Flowable<List<User>>

    @Query("SELECT * FROM users WHERE userEmail = :email LIMIT 1")
    fun getUserByEmail(email: String): Flowable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

    @Query("DELETE FROM users WHERE userId = :id")
    fun deleteUser(id: Long = 0)
}