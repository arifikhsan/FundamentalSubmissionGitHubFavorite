package com.arifikhsan.githubfavorite.database

import androidx.room.*
import com.arifikhsan.githubfavorite.entity.UserEntity

interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): ArrayList<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userEntity: UserEntity)

    @Update
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Query("DELETE FROM users")
    fun deleteAll()
}