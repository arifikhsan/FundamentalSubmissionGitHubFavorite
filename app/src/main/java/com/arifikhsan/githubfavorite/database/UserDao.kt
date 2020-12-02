package com.arifikhsan.githubfavorite.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arifikhsan.githubfavorite.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()
}