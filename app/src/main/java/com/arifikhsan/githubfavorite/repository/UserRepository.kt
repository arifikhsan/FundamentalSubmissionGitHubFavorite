package com.arifikhsan.githubfavorite.repository

import com.arifikhsan.githubfavorite.database.UserDao
import com.arifikhsan.githubfavorite.entity.User

class UserRepository(private val userDao: UserDao) {

    fun allUsers(): List<User> {
        return userDao.getUsers()
    }

    fun insert(user: User) {
        userDao.insertUser(user)
    }
}