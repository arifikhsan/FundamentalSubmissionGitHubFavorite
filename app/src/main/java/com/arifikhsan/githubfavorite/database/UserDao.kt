package com.arifikhsan.githubfavorite.database

import androidx.room.*
import com.arifikhsan.githubfavorite.entity.User
import kotlinx.coroutines.flow.Flow

interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): Flow<ArrayList<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()
}