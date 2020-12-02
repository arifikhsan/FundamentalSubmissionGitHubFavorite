package com.arifikhsan.githubfavorite.repository

import com.arifikhsan.githubfavorite.database.UserDao
import com.arifikhsan.githubfavorite.entity.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    val allUsers: Flow<ArrayList<User>> = userDao.getUsers()

    fun insert(user: User) {
        userDao.insertUser(user)
    }
}