package com.arifikhsan.githubfavorite.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.arifikhsan.githubfavorite.database.AppDatabase
import com.arifikhsan.githubfavorite.database.UserDao
import com.arifikhsan.githubfavorite.entity.User

class UserRepository(application: Application) {

    private var userDao: UserDao
    var allUsers: List<User>

    init {
        val database = AppDatabase.getDatabase(application)
        userDao = database.userDao()
        allUsers = userDao.getUsers().toCollection(ArrayList())
    }

    fun insert(user: User) {
        userDao.insertUser(user)
    }

    fun delete(user: User) {
        userDao.deleteUser(user)
    }
}