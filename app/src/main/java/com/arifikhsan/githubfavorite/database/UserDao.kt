package com.arifikhsan.githubfavorite.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.arifikhsan.githubfavorite.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers(): Cursor

    @Query("SELECT * FROM users WHERE login = :username")
    fun findByUsername(username: String): Cursor

    @Query("SELECT * FROM users WHERE id = :id")
    fun findById(id: Int): Cursor

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User): Long

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteById(id: Int): Int

    @Query("DELETE FROM users")
    fun deleteAll()
}