package com.arifikhsan.githubfavorite.repository

import com.arifikhsan.githubfavorite.database.UserDao
import com.arifikhsan.githubfavorite.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    val allUsers: Flow<ArrayList<UserEntity>> = userDao.getUsers()

    fun insert(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }
}